package com.joyin.fyzg.utils;

import com.joyin.fyzg.config.redis.RedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.*;
import redis.clients.util.Pool;
import redis.clients.util.SafeEncoder;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author:xiongyang
 * @Description:
 * @Date: 10:45 2018/6/25 0025
 */
@Slf4j
public class JedisUtil {

    public static final int DEFAULT_EXPIRE_TIME = 7200; // 默认过期时间,单位/秒, 60*60*2=2H, 两小时

    /**
     * 缓存生存时间
     */
    private final int expire = 60000;
    /** 操作Key的方法 */
    public static Keys KEYS;
    /** 对存储结构为String类型的操作 */
    public static Strings STRINGS;
    /** 对存储结构为List类型的操作 */
    public static Lists LISTS;
    /** 对存储结构为Set类型的操作 */
    public static Sets SETS;
    /** 对存储结构为HashMap类型的操作 */
    public static Hash HASH;
    public static SeqGenerator SEQ;
    /** 对存储结构为Set(排序的)类型的操作 */
    public static SortSet SORTSET;
    private static Pool jedisPool = null;

    private RedisProperties redisProperties;

    public JedisUtil(RedisProperties redisProperties) {
        init(redisProperties);
    }

    private JedisUtil() {
    }
    public void init(){
        if (jedisPool == null){
            getInstance();
        }
        if (HASH == null){
            HASH = new Hash();
        }
        if (LISTS == null){
            LISTS = new Lists();
        }
        if (SETS == null){
            SETS = new Sets();
        }
        if (KEYS == null){
            KEYS = new Keys();
        }
        if (STRINGS == null){
            STRINGS = new Strings();
        }
        if (SEQ == null){
            SEQ = new SeqGenerator();
        }
    }
    public void init(RedisProperties redisProperties){
        this.redisProperties = redisProperties;
        init();
    }


    /**
     * 构建redis连接池
     * @param
     * @param
     * @return JedisPool
     */
    private void getInstance() {
        if (jedisPool == null) {
            JedisPoolConfig config = new JedisPoolConfig();

            config.setMaxTotal(200);			// 最大连接数, 默认8个
            config.setMaxIdle(50);				// 最大空闲连接数, 默认8个
            config.setMinIdle(8);				// 设置最小空闲数
            config.setMaxWaitMillis(10000);		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
            config.setTestOnBorrow(true);		// 在获取连接的时候检查有效性, 默认false
            config.setTestOnReturn(true);       // 调用returnObject方法时，是否进行有效检查
            config.setTestWhileIdle(true);		// Idle时进行连接扫描
            config.setTimeBetweenEvictionRunsMillis(30000);	//表示idle object evitor两次扫描之间要sleep的毫秒数
            config.setNumTestsPerEvictionRun(10);			//表示idle object evitor每次扫描的最多的对象数
            config.setMinEvictableIdleTimeMillis(60000);	//表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
            // JedisShardInfo List

            String redisType = redisProperties.getRedisType();
            switch (redisType){
                case RedisProperties.TYPE_ALONE :
                    log.info("Redis 当前模式---------------->：单机");
                    if (StringUtils.isBlank(redisProperties.getAloneIp()) ||
                            redisProperties.getAlonePort() == null ){
                        log.error("警告！！！！Redis当前配置模式为单机，但未配置地址或者端口！！！！！");
                        log.error("警告！！！！Redis当前配置模式为单机，但未配置地址或者端口！！！！！");
                        log.error("警告！！！！Redis当前配置模式为单机，但未配置地址或者端口！！！！！");
                    }
                    if (StringUtils.isBlank(redisProperties.getPassWord())){
                        jedisPool = new JedisPool(config, redisProperties.getAloneIp(), redisProperties.getAlonePort()
                                ,10000);
                    }else {
                        jedisPool = new JedisPool(config, redisProperties.getAloneIp(), redisProperties.getAlonePort()
                                ,10000,redisProperties.getPassWord());
                    }
                    break;
                case RedisProperties.TYPE_SENTINEL :
                    log.info("Redis 当前模式---------------->：哨兵");
                    if (StringUtils.isBlank(redisProperties.getMasterName())
                            || redisProperties.getRedisSentinels() == null){
                        log.error("警告！！！！Redis当前配置模式为哨兵，但未配置监听节点名称或者哨兵集群！！！！！");
                        log.error("警告！！！！Redis当前配置模式为哨兵，但未配置监听节点名称或者哨兵集群！！！！！");
                        log.error("警告！！！！Redis当前配置模式为哨兵，但未配置监听节点名称或者哨兵集群！！！！！");
                    }
                    if (StringUtils.isBlank(redisProperties.getPassWord())) {
                        jedisPool = new JedisSentinelPool(redisProperties.getMasterName()
                                , new HashSet<>(redisProperties.getRedisSentinels()), config);
                    }else {
                        jedisPool = new JedisSentinelPool(redisProperties.getMasterName()
                                , new HashSet<>(redisProperties.getRedisSentinels()), config, redisProperties.getPassWord());
                    }
                    break;
                case RedisProperties.TYPE_CLUSTER :
                    log.info("Redis 当前模式---------------->：集群");
                    //todo
                    break;
            }

            log.info(">>>>>>>>>>> base-common, JedisUtil.ShardedJedisPool init success.");
        }
    }

    public Pool getPool() {
        return jedisPool;
    }

    /**
     * 从jedis连接池中获取获取jedis对象
     * @return
     */
    public Jedis getJedis() {
        return (Jedis)jedisPool.getResource();
    }

    /**
     * 回收jedis
     * @param jedis
     */
    public void returnJedis(Jedis jedis) {
        jedis.close();
        /*jedisPool.returnResource(jedis);*/
    }


    /**
     * 设置过期时间
     *
     * @author ruan 2013-4-11
     * @param key
     * @param seconds
     */
    public void expire(String key, int seconds) {
        if (seconds <= 0) {
            return;
        }
        executeOperate(jedis -> {
            jedis.expire(key, seconds);
            return null;
        });
    }

    /**
     * 设置默认过期时间
     *
     * @author ruan 2013-4-11
     * @param key
     */
    public void expire(String key) {
        expire(key, expire);
    }


    //*******************************************Keys*******************************************//
    public class Keys {

        /**
         * 清空所有key
         */
        public String flushAll() {
            return executeOperate(jedis -> jedis.flushAll());
        }

        /**
         * 更改key
         *
         * @param
         *            oldkey
         * @param
         *            newkey
         * @return 状态码
         * */
        public String rename(String oldkey, String newkey) {
            return rename(SafeEncoder.encode(oldkey),
                    SafeEncoder.encode(newkey));
        }

        /**
         * 更改key,仅当新key不存在时才执行
         *
         * @param
         *            oldkey
         * @param
         *            newkey
         * @return 状态码
         * */
        public long renamenx(String oldkey, String newkey) {
            return executeOperate(jedis -> jedis.renamenx(oldkey, newkey));
        }

        /**
         * 更改key
         *
         * @param
         *            oldkey
         * @param
         *            newkey
         * @return 状态码
         * */
        public String rename(byte[] oldkey, byte[] newkey) {
            return executeOperate(jedis -> jedis.rename(oldkey, newkey));
        }

        /**
         * 设置key的过期时间，以秒为单位
         *
         * @param
         *            key
         * @param
         *
         * @return 影响的记录数
         * */
        public long expired(String key, int seconds) {
            return executeOperate(jedis -> jedis.expire(key, seconds));
        }

        /**
         * 设置key的过期时间,它是距历元（即格林威治标准时间 1970 年 1 月 1 日的 00:00:00，格里高利历）的偏移量。
         *
         * @param
         *            key
         * @param
         *
         * @return 影响的记录数
         * */
        public long expireAt(String key, long timestamp) {
            return executeOperate(jedis -> jedis.expireAt(key, timestamp));
        }

        /**
         * 查询key的过期时间
         *
         * @param
         *            key
         * @return 以秒为单位的时间表示
         * */
        public long ttl(String key) {
            return executeOperate(jedis -> jedis.ttl(key));
        }

        /**
         * 取消对key过期时间的设置
         *
         * @param key
         * @return 影响的记录数
         * */
        public long persist(String key) {
            return executeOperate(jedis -> jedis.persist(key));
        }

        /**
         * 删除keys对应的记录,可以是多个key
         *
         * @param
         *             keys
         * @return 删除的记录数
         * */
        public long del(String... keys) {
            return executeOperate(jedis -> jedis.del(keys));
        }

        /**
         * 删除keys对应的记录,可以是多个key
         *
         * @param
         *             keys
         * @return 删除的记录数
         * */
        public long del(byte[]... keys) {
            return executeOperate(jedis -> jedis.del(keys) );
        }

        /**
         * 判断key是否存在
         *
         * @param
         *            key
         * @return boolean
         * */
        public boolean exists(String key) {
            return executeOperate(jedis -> jedis.exists(key));
        }

        /**
         * 对List,Set,SortSet进行排序,如果集合数据较大应避免使用这个方法
         *
         * @param
         *            key
         * @return List<String> 集合的全部记录
         * **/
        public List<String> sort(String key) {
            return executeOperate(jedis -> jedis.sort(key));
        }

        /**
         * 对List,Set,SortSet进行排序或limit
         *
         * @param
         *            key
         * @param
         *            parame 定义排序类型或limit的起止位置.
         * @return List<String> 全部或部分记录
         * **/
        public List<String> sort(String key, SortingParams parame) {
            return executeOperate(jedis -> jedis.sort(key, parame));
        }

        /**
         * 返回指定key存储的类型
         *
         * @param
         *            key
         * @return String string|list|set|zset|hash
         * **/
        public String type(String key) {
            return executeOperate(jedis -> jedis.type(key));
        }

        /**
         * 查找所有匹配给定的模式的键
         *
         * @param
         *            ,*表示多个，？表示一个
         * */
        public Set<String> keys(String pattern) {
            return executeOperate(jedis -> jedis.keys(pattern));
        }
    }

    //*******************************************Sets*******************************************//
    public class Sets {

        /**
         * 向Set添加一条记录，如果member已存在返回0,否则返回1
         *
         * @param
         *            key
         * @param
         *            member
         * @return 操作码,0或1
         * */
        public long sadd(String key, String member) {
            return executeOperate(jedis -> jedis.sadd(key, member));
        }

        public long sadd(byte[] key, byte[] member) {
            return executeOperate(jedis -> jedis.sadd(key, member));
        }

        /**
         * 获取给定key中元素个数
         *
         * @param
         *            key
         * @return 元素个数
         * */
        public long scard(String key) {
            return executeOperate(jedis -> jedis.scard(key));
        }

        /**
         * 返回从第一组和所有的给定集合之间的差异的成员
         *
         * @param
         *             keys
         * @return 差异的成员集合
         * */
        public Set<String> sdiff(String... keys) {
            return executeOperate(jedis -> jedis.sdiff(keys));
        }

        /**
         * 这个命令等于sdiff,但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
         *
         * @param
         *            newkey 新结果集的key
         * @param
         *             keys 比较的集合
         * @return 新集合中的记录数
         * **/
        public long sdiffstore(String newkey, String... keys) {
            return executeOperate(jedis -> jedis.sdiffstore(newkey, keys));
        }

        /**
         * 返回给定集合交集的成员,如果其中一个集合为不存在或为空，则返回空Set
         *
         * @param
         *             keys
         * @return 交集成员的集合
         * **/
        public Set<String> sinter(String... keys) {
            return executeOperate(jedis -> jedis.sinter(keys));
        }

        /**
         * 这个命令等于sinter,但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
         *
         * @param
         *            newkey 新结果集的key
         * @param
         *             keys 比较的集合
         * @return 新集合中的记录数
         * **/
        public long sinterstore(String newkey, String... keys) {
            return executeOperate(jedis -> jedis.sinterstore(newkey, keys));
        }

        /**
         * 确定一个给定的值是否存在
         *
         * @param
         *            key
         * @param
         *            member 要判断的值
         * @return 存在返回1，不存在返回0
         * **/
        public boolean sismember(String key, String member) {
            return executeOperate(jedis -> jedis.sismember(key, member));
        }

        /**
         * 返回集合中的所有成员
         *
         * @param
         *            key
         * @return 成员集合
         * */
        public Set<String> smembers(String key) {
            return executeOperate(jedis -> jedis.smembers(key));
        }

        public Set<byte[]> smembers(byte[] key) {
            return executeOperate(jedis -> jedis.smembers(key));
        }

        /**
         * 将成员从源集合移出放入目标集合 <br/>
         * 如果源集合不存在或不包哈指定成员，不进行任何操作，返回0<br/>
         * 否则该成员从源集合上删除，并添加到目标集合，如果目标集合中成员已存在，则只在源集合进行删除
         *
         * @param
         *            srckey 源集合
         * @param
         *            dstkey 目标集合
         * @param
         *            member 源集合中的成员
         * @return 状态码，1成功，0失败
         * */
        public long smove(String srckey, String dstkey, String member) {
            return executeOperate(jedis -> jedis.smove(srckey, dstkey, member));
        }

        /**
         * 从集合中删除成员
         *
         * @param
         *            key
         * @return 被删除的成员
         * */
        public String spop(String key) {
            return executeOperate(jedis -> jedis.spop(key));
        }

        /**
         * 从集合中删除指定成员
         *
         * @param
         *            key
         * @param
         *            member 要删除的成员
         * @return 状态码，成功返回1，成员不存在返回0
         * */
        public long srem(String key, String member) {
            return executeOperate(jedis -> jedis.srem(key, member));
        }

        /**
         * 合并多个集合并返回合并后的结果，合并后的结果集合并不保存<br/>
         *
         * @param
         *             keys
         * @return 合并后的结果集合
         * @see
         * */
        public Set<String> sunion(String... keys) {
            return executeOperate(jedis -> jedis.sunion(keys));
        }

        /**
         * 合并多个集合并将合并后的结果集保存在指定的新集合中，如果新集合已经存在则覆盖
         *
         * @param
         *            newkey 新集合的key
         * @param
         *             keys 要合并的集合
         * **/
        public long sunionstore(String newkey, String... keys) {
            return executeOperate(jedis -> jedis.sunionstore(newkey, keys) );
        }
    }

    //*******************************************SortSet*******************************************//
    public class SortSet {

        /**
         * 向集合中增加一条记录,如果这个值已存在，这个值对应的权重将被置为新的权重
         *
         * @param
         *            key
         * @param  score 权重
         * @param
         *            member 要加入的值，
         * @return 状态码 1成功，0已存在member的值
         * */
        public long zadd(String key, double score, String member) {
            return executeOperate(jedis -> jedis.zadd(key, score, member));
        }

        public long zadd(String key, Map<String,Double > scoreMembers) {
            return executeOperate(jedis -> jedis.zadd(key, scoreMembers));
        }

        /**
         * 获取集合中元素的数量
         *
         * @param
         *            key
         * @return 如果返回0则集合不存在
         * */
        public long zcard(String key) {
            return executeOperate(jedis -> jedis.zcard(key));
        }

        /**
         * 获取指定权重区间内集合的数量
         *
         * @param
         *            key
         * @param  min 最小排序位置
         * @param  max 最大排序位置
         * */
        public long zcount(String key, double min, double max) {
            return executeOperate(jedis -> jedis.zcount(key, min, max));
        }

        /**
         * 获得set的长度
         *
         * @param key
         * @return
         */
        public long zlength(String key) {
            long len = 0;
            Set<String> set = zrange(key, 0, -1);
            len = set.size();
            return len;
        }

        /**
         * 权重增加给定值，如果给定的member已存在
         *
         * @param
         *            key
         * @param  score 要增的权重
         * @param
         *            member 要插入的值
         * @return 增后的权重
         * */
        public double zincrby(String key, double score, String member) {
            return executeOperate(jedis -> jedis.zincrby(key, score, member));
        }

        /**
         * 返回指定位置的集合元素,0为第一个元素，-1为最后一个元素
         *
         * @param
         *            key
         * @param  start 开始位置(包含)
         * @param  end 结束位置(包含)
         * @return Set<String>
         * */
        public Set<String> zrange(String key, int start, int end) {
            return executeOperate(jedis -> jedis.zrange(key, start, end));
        }

        /**
         * 返回指定权重区间的元素集合
         *
         * @param
         *            key
         * @param  min 上限权重
         * @param  max 下限权重
         * @return Set<String>
         * */
        public Set<String> zrangeByScore(String key, double min, double max) {
            return executeOperate(jedis -> jedis.zrangeByScore(key, min, max));
        }

        /**
         * 获取指定值在集合中的位置，集合排序从低到高
         *
         * @see
         * @param
         *            key
         * @param
         *            member
         * @return long 位置
         * */
        public long zrank(String key, String member) {
            return executeOperate(jedis -> jedis.zrank(key, member));
        }

        /**
         * 获取指定值在集合中的位置，集合排序从高到低
         *
         * @see
         * @param  key
         * @param  member
         * @return long 位置
         * */
        public long zrevrank(String key, String member) {
            return executeOperate(jedis -> jedis.zrevrank(key, member));
        }

        /**
         * 从集合中删除成员
         *
         * @param  key
         * @param  member
         * @return 返回1成功
         * */
        public long zrem(String key, String member) {
            return executeOperate(jedis -> jedis.zrem(key, member));
        }

        /**
         * 删除
         *
         * @param key
         * @return
         */
        public long zrem(String key) {
            return executeOperate(jedis -> jedis.del(key));
        }

        /**
         * 删除给定位置区间的元素
         *
         * @param
         *            key
         * @param  start 开始区间，从0开始(包含)
         * @param  end 结束区间,-1为最后一个元素(包含)
         * @return 删除的数量
         * */
        public long zremrangeByRank(String key, int start, int end) {
            return executeOperate(jedis -> jedis.zremrangeByRank(key, start, end));
        }

        /**
         * 删除给定权重区间的元素
         *
         * @param
         *            key
         * @param  min 下限权重(包含)
         * @param  max 上限权重(包含)
         * @return 删除的数量
         * */
        public long zremrangeByScore(String key, double min, double max) {
            return executeOperate(jedis -> jedis.zremrangeByScore(key, min, max));
        }

        /**
         * 获取给定区间的元素，原始按照权重由高到低排序
         *
         * @param   key
         * @param  start
         * @param  end
         * @return Set<String>
         * */
        public Set<String> zrevrange(String key, int start, int end) {
            return executeOperate(jedis -> jedis.zrevrange(key, start, end) );
        }

        /**
         * 获取给定值在集合中的权重
         * @param   key
         * @param
         * @return double 权重
         * */
        public double zscore(String key, String memebr) {
            return executeOperate(jedis -> {
                Double score = jedis.zscore(key, memebr);
                if (score != null)
                    return score;
                return 0.00;
            });
        }
    }

    //*******************************************Hash*******************************************//
    public class Hash {

        /**
         * 从hash中删除指定的存储
         * @param  key
         * @param   fieid 存储的名字
         * @return 状态码，1成功，0失败
         * */
        public long hdel(String key, String fieid) {
            return executeOperate(jedis -> jedis.hdel(key, fieid));
        }

        public long hdel(String key) {
            return executeOperate(jedis -> jedis.del(key));
        }

        /**
         * 测试hash中指定的存储是否存在
         * @param  key
         * @param   fieid 存储的名字
         * @return 1存在，0不存在
         * */
        public boolean hexists(String key, String fieid) {
            return executeOperate(jedis -> jedis.hexists(key, fieid));
        }

        /**
         * 返回hash中指定存储位置的值
         *
         * @param  key
         * @param  fieid 存储的名字
         * @return 存储对应的值
         * */
        public String hget(String key, String fieid) {
            return executeOperate(jedis -> jedis.hget(key, fieid));
        }

        public byte[] hget(byte[] key, byte[] fieid) {
            return executeOperate(jedis -> jedis.hget(key, fieid));
        }

        /**
         * 以Map的形式返回hash中的存储和值
         * @param     key
         * @return Map<Strinig,String>
         * */
        public Map<String, String> hgetAll(String key) {
            return executeOperate(jedis -> jedis.hgetAll(key) );
        }

        /**
         * 添加一个对应关系
         * @param   key
         * @param  fieid
         * @param  value
         * @return 状态码 1成功，0失败，fieid已存在将更新，也返回0
         * **/
        public long hset(String key, String fieid, String value) {
            return executeOperate(jedis -> jedis.hset(key, fieid, value));
        }

        public long hset(String key, String fieid, byte[] value) {
            return executeOperate(jedis -> jedis.hset(key.getBytes(), fieid.getBytes(), value));
        }

        /**
         * 添加对应关系，只有在fieid不存在时才执行
         * @param  key
         * @param  fieid
         * @param  value
         * @return 状态码 1成功，0失败fieid已存
         * **/
        public long hsetnx(String key, String fieid, String value) {
            return executeOperate(jedis -> jedis.hsetnx(key, fieid, value));
        }

        /**
         * 获取hash中value的集合
         *
         * @param
         *            key
         * @return List<String>
         * */
        public List<String> hvals(String key) {
            return executeOperate(jedis -> jedis.hvals(key));
        }

        /**
         * 在指定的存储位置加上指定的数字，存储位置的值必须可转为数字类型
         *
         * @param
         *            key
         * @param
         *            fieid 存储位置
         * @param
         *             value 要增加的值,可以是负数
         * @return 增加指定数字后，存储位置的值
         * */
        public long hincrby(String key, String fieid, long value) {
            return executeOperate(jedis -> jedis.hincrBy(key, fieid, value));
        }

        /**
         * 返回指定hash中的所有存储名字,类似Map中的keySet方法
         *
         * @param
         *            key
         * @return Set<String> 存储名称的集合
         * */
        public Set<String> hkeys(String key) {
            return executeOperate(jedis -> jedis.hkeys(key));
        }

        /**
         * 获取hash中存储的个数，类似Map中size方法
         *
         * @param
         *            key
         * @return long 存储的个数
         * */
        public long hlen(String key) {
            return executeOperate(jedis -> jedis.hlen(key));
        }

        /**
         * 根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
         *
         * @param
         *            key
         * @param
         *            fieids 存储位置
         * @return List<String>
         * */
        public List<String> hmget(String key, String... fieids) {
            return executeOperate(jedis -> jedis.hmget(key, fieids));
        }

        public List<byte[]> hmget(byte[] key, byte[]... fieids) {
            return executeOperate(jedis -> jedis.hmget(key, fieids));
        }

        /**
         * 添加对应关系，如果对应关系已存在，则覆盖
         *
         * @param
         *            key
         * @param
         *            <String,String> 对应关系
         * @return 状态，成功返回OK
         * */
        public String hmset(String key, Map<String, String> map) {
            return executeOperate(jedis -> jedis.hmset(key, map));
        }

        /**
         * 添加对应关系，如果对应关系已存在，则覆盖
         *
         * @param
         *            key
         * @param
         *            <String,String> 对应关系
         * @return 状态，成功返回OK
         * */
        public String hmset(byte[] key, Map<byte[], byte[]> map) {
            return executeOperate(jedis -> jedis.hmset(key, map));
        }

    }


    //*******************************************Strings*******************************************//
    public class Strings {
        /**
         * 根据key获取记录
         * @param   key
         * @return 值
         * */
        public String get(String key) {
            return executeOperate(jedis -> jedis.get(key));
        }
        public Map get4Vague(String key) {
            return executeOperate(jedis -> {
                Map<String,String> res = new HashMap();
                ScanParams params = new ScanParams();
                params.match(key+"*");
                Jedis sjedis = getJedis();
                String cursor = "0";
                while (true) {
                    ScanResult<String> scanResult = sjedis.scan(cursor, params);
                    List<String> elements = scanResult.getResult();
                    if (elements != null && elements.size() > 0) {
                        for (String keyTemp : elements){
                            String s = this.get(keyTemp);
                            res.put(keyTemp,s);
                        }
                    }
                    cursor = scanResult.getStringCursor();
                    if ("0".equals(cursor)) {
                        break;
                    }
                }
                return res;
            });
        }

        /**
         * 根据key获取记录
         * @param  key
         * @return 值
         * */
        public byte[] get(byte[] key) {
            return executeOperate(jedis -> jedis.get(key));
        }

        /**
         * 添加有过期时间的记录
         *
         * @param   key
         * @param  seconds 过期时间，以秒为单位
         * @param  value
         * @return String 操作状态
         * */
        public String setEx(String key, int seconds, String value) {
            return executeOperate(jedis -> jedis.setex(key, seconds, value));
        }

        /**
         * 添加有过期时间的记录
         *
         * @param  key
         * @param  seconds 过期时间，以秒为单位
         * @param   value
         * @return String 操作状态
         * */
        public String setEx(byte[] key, int seconds, byte[] value) {
            return executeOperate(jedis -> jedis.setex(key, seconds, value) );
        }

        /**
         * 添加一条记录，仅当给定的key不存在时才插入
         * @param  key
         * @param  value
         * @return long 状态码，1插入成功且key不存在，0未插入，key存在
         * */
        public long setnx(String key, String value) {
           return executeOperate(jedis -> jedis.setnx(key, value));
        }

        /**
         * 添加记录,如果记录已存在将覆盖原有的value
         * @param  key
         * @param  value
         * @return 状态码
         * */
        public String set(String key, String value) {
            return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
        }

        /**
         * 添加记录,如果记录已存在将覆盖原有的value
         * @param   key
         * @param  value
         * @return 状态码
         * */
        public String set(String key, byte[] value) {
            return set(SafeEncoder.encode(key), value);
        }

        /**
         * 添加记录,如果记录已存在将覆盖原有的value
         * @param  key
         * @param  value
         * @return 状态码
         * */
        public String set(byte[] key, byte[] value) {
            return executeOperate(jedis -> jedis.set(key ,value) );
        }

        /**
         * 从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据<br/>
         * 例:String str1="123456789";<br/>
         * 对str1操作后setRange(key,4,0000)，str1="123400009";
         * @param   key
         * @param  offset
         * @param   value
         * @return long value的长度
         * */
        public long setRange(String key, long offset, String value) {
            return executeOperate(jedis -> jedis.setrange(key, offset, value));
        }

        /**
         * 在指定的key中追加value
         * @param   key
         * @param  value
         * @return long 追加后value的长度
         * **/
        public long append(String key, String value) {
            return executeOperate(jedis -> jedis.append(key, value));
        }

        /**
         * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用
         *
         * @param
         *            key
         * @param  number 要减去的值
         * @return long 减指定值后的值
         * */
        public long decrBy(String key, long number) {
            return executeOperate(jedis -> jedis.decrBy(key, number) );
        }

        /**
         * <b>可以作为获取唯一id的方法</b><br/>
         * 将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
         * @param   key
         * @param  number 要减去的值
         * @return long 相加后的值
         * */
        public long incrBy(String key, long number) {
            return executeOperate(jedis -> jedis.incrBy(key, number));
        }

        /**
         * 对指定key对应的value进行截取
         * @param    key
         * @param  startOffset 开始位置(包含)
         * @param  endOffset 结束位置(包含)
         * @return String 截取的值
         * */
        public String getrange(String key, long startOffset, long endOffset) {
            return executeOperate(jedis -> jedis.getrange(key, startOffset, endOffset));
        }

        /**
         * 获取并设置指定key对应的value<br/>
         * 如果key存在返回之前的value,否则返回null
         * @param   key
         * @param  value
         * @return String 原始value或null
         * */
        public String getSet(String key, String value) {
            return executeOperate(jedis -> jedis.getSet(key, value));
        }

        /**
         * 批量获取记录,如果指定的key不存在返回List的对应位置将是null
         * @param  keys
         * @return List<String> 值得集合
         * */
        public List<String> mget(String... keys) {
            return executeOperate(jedis -> jedis.mget(keys));
        }

        /**
         * 批量存储记录
         * @param  keysvalues 例:keysvalues="key1","value1","key2","value2";
         * @return String 状态码
         * */
        public String mset(String... keysvalues) {
            return executeOperate(jedis -> jedis.mset(keysvalues));
        }

        /**
         * 获取key对应的值的长度
         * @param  key
         * @return value值得长度
         * */
        public long strlen(String key) {
            return executeOperate(jedis -> jedis.strlen(key));
        }
    }


    //*******************************************Lists*******************************************//
    public class Lists {
        /**
         * List长度
         * @param  key
         * @return 长度
         * */
        public long llen(String key) {
            return llen(SafeEncoder.encode(key));
        }

        /**
         * List长度
         * @param  key
         * @return 长度
         * */
        public long llen(byte[] key) {
            return executeOperate(jedis -> jedis.llen(key));
        }

        /**
         * 覆盖操作,将覆盖List中指定位置的值
         * @param  key
         * @param  index 位置
         * @param  value 值
         * @return 状态码
         * */
        public String lset(byte[] key, int index, byte[] value) {
            return executeOperate(jedis -> jedis.lset(key, index, value));
        }

        /**
         * 覆盖操作,将覆盖List中指定位置的值
         * @param key
         * @param  index 位置
         * @param   value 值
         * @return 状态码
         * */
        public String lset(String key, int index, String value) {
            return lset(SafeEncoder.encode(key), index,
                    SafeEncoder.encode(value));
        }

        /**
         * 获取List中指定位置的值
         * @param   key
         * @param  index 位置
         * @return 值
         * **/
        public String lindex(String key, int index) {
            return SafeEncoder.encode(lindex(SafeEncoder.encode(key), index));
        }

        /**
         * 获取List中指定位置的值
         * @param  key
         * @param  index 位置
         * @return 值
         * **/
        public byte[] lindex(byte[] key, int index) {
            return executeOperate(jedis -> jedis.lindex(key, index) );
        }

        /**
         * 将List中的第一条记录移出List
         * @param  key
         * @return 移出的记录
         * */
        public String lpop(String key) {
            return SafeEncoder.encode(lpop(SafeEncoder.encode(key)));
        }

        /**
         * 将List中的第一条记录移出List
         * @param  key
         * @return 移出的记录
         * */
        public byte[] lpop(byte[] key) {
            return executeOperate(jedis -> jedis.lpop(key));
        }

        /**
         * 将List中最后第一条记录移出List
         *
         * @param  key
         * @return 移出的记录
         * */
        public String rpop(String key) {
            return executeOperate(jedis -> jedis.rpop(key));
        }

        /**
         * 向List尾部追加记录
         * @param  key
         * @param  value
         * @return 记录总数
         * */
        public long lpush(String key, String value) {
            return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
        }

        /**
         * 向List头部追加记录
         * @param   key
         * @param   value
         * @return 记录总数
         * */
        public long rpush(String key, String value) {
            return executeOperate(jedis ->  jedis.rpush(key, value));
        }

        public long linsertBefore(String key, String v1, String v2) {
            return executeOperate(jedis ->  jedis.linsert(key, BinaryClient.LIST_POSITION.BEFORE, v1 , v2));
        }

        /**
         * 向List头部追加记录，如果存在则不添加
         * @param   key
         * @param   value
         * @return 记录总数
         * */
        public synchronized long rPush4NotExist(String key, String value) {
            return executeOperate(jedis ->  {
                final List<String> r1 = jedis.lrange(key, 0, -1);
                if (!r1.contains(value)){
                    jedis.rpush(key, value);
                }
                return 1L;
            });
        }

        /**
         * 向List头部追加记录
         * @param  key
         * @param  value
         * @return 记录总数
         * */
        public long rpush(byte[] key, byte[] value) {
            return executeOperate(jedis -> jedis.rpush(key, value));
        }

        /**
         * 向List中追加记录
         * @param  key
         * @param  value
         * @return 记录总数
         * */
        public long lpush(byte[] key, byte[] value) {
            return executeOperate(jedis -> jedis.lpush(key, value));
        }

        /**
         * 获取指定范围的记录，可以做为分页使用
         * @param  key
         * @param  start
         * @param  end
         * @return List
         * */
        public List<String> lrange(String key, long start, long end) {
            return executeOperate(jedis -> jedis.lrange(key, start, end));
        }

        /**
         * 获取指定范围的记录，可以做为分页使用
         * @param  key
         * @param  start
         * @param  end 如果为负数，则尾部开始计算
         * @return List
         * */
        public List<byte[]> lrange(byte[] key, int start, int end) {
            return executeOperate(jedis -> jedis.lrange(key, start, end));
        }

        /**
         * 删除List中c条记录，被删除的记录值为value
         * @param  key
         * @param  c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
         * @param  value 要匹配的值
         * @return 删除后的List中的记录数
         * */
        public long lrem(byte[] key, int c, byte[] value) {
            return executeOperate(jedis -> jedis.lrem(key, c, value));
        }

        /**
         * 删除List中c条记录，被删除的记录值为value
         * @param  key
         * @param  c 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
         * @param  value 要匹配的值
         * @return 删除后的List中的记录数
         * */
        public long lrem(String key, int c, String value) {
            return lrem(SafeEncoder.encode(key), c, SafeEncoder.encode(value));
        }

        /**
         * 算是删除吧，只保留start与end之间的记录
         * @param  key
         * @param  start 记录的开始位置(0表示第一条记录)
         * @param  end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
         * @return 执行状态码
         * */
        public String ltrim(byte[] key, int start, int end) {
            return executeOperate(jedis -> jedis.ltrim(key, start, end));
        }

        /**
         * 算是删除吧，只保留start与end之间的记录
         * @param  key
         * @param  start 记录的开始位置(0表示第一条记录)
         * @param  end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
         * @return 执行状态码
         * */
        public String ltrim(String key, int start, int end) {
            return ltrim(SafeEncoder.encode(key), start, end);
        }
    }

    public class SeqGenerator{

        public String getId() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            String formatDate = sdf.format(date);
            String key = "key" + formatDate;
            Long incr = getIncr(key, getCurrent2TodayEndMillisTime());
            if (incr == 0) {
                incr = getIncr(key, getCurrent2TodayEndMillisTime());//从001开始
            }
            DecimalFormat df = new DecimalFormat("000");//三位序列号
            return formatDate + df.format(incr);
        }
        public String getContextId() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            String formatDate = sdf.format(date);
            String key = "ci" + formatDate;
            Long incr = getIncr(key, getCurrent2TodayEndMillisTime());
            if (incr == 0) {
                incr = getIncr(key, getCurrent2TodayEndMillisTime());//从001开始
            }
            DecimalFormat df = new DecimalFormat("00000000");//三位序列号
            return formatDate + df.format(incr);
        }
        private long getCurrent2TodayEndMillisTime() {
            Calendar todayEnd = Calendar.getInstance();
            // Calendar.HOUR 12小时制
            // HOUR_OF_DAY 24小时制
            todayEnd.set(Calendar.HOUR_OF_DAY, 23);
            todayEnd.set(Calendar.MINUTE, 59);
            todayEnd.set(Calendar.SECOND, 59);
            todayEnd.set(Calendar.MILLISECOND, 999);
            return todayEnd.getTimeInMillis() - new Date().getTime();
        }
        private Long getIncr(String key, long liveTime) {
            return executeOperate(jedis -> {
                String jedisKey = key ;//key+prefix，作为key存入redis,value
                Transaction tx = jedis.multi();//开启事务
                Response<Long> incr = tx.incr(jedisKey);//redis自增，如果没有这个key，redis会自己创建
                JedisUtil.KEYS.expired(jedisKey,Integer.parseInt(liveTime+""));
                tx.exec();//执行事务
                long a = incr.get().longValue();//获取自增键的值
                //a += 1000000;//前面补充0,转String类型后用substring(1)把1去掉
                //String result = jedisKey + String.valueOf(a).substring(1);
                return a;
            });
        }
    }
    @FunctionalInterface
    interface JedisFunction<T> {
        T executeJedisOperate(Jedis jedis);
    }

    public <T> T executeOperate(JedisFunction<T> function){
        Jedis jedis = getJedis();
        try {
            return function.executeJedisOperate(jedis);
        } finally {
            returnJedis(jedis);
        }
    }
}
