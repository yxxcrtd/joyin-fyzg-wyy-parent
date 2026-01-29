package com.joyin.fyzg.utils;

import org.apache.commons.lang.StringUtils;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * GB 2312-80 把收录的汉字分成两级。第一级汉字是常用汉字，计 3755 个， 置于 16～55
 * 区，按汉语拼音字母／笔形顺序排列；第二级汉字是次常用汉字， 计 3008 个，置于 56～87 区，按部首／笔画顺序排列，所以本程序只能查到
 * 对一级汉字的声母。同时对符合声母（zh，ch，sh）只能取首字母（z，c，s）
 * <br/>
 * @author pidong
 * @date 2020/4/1 17:47
 */
public class EncodeUtil {

    /** 汉字区位码 */
	static final int LI_SECPOSVALUE[] = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590 };
	/** 存放国标一级汉字不同读音的起始区位码对应读音 */
	static final char LC_FIRSTLETTER[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'W', 'X', 'Y', 'Z' };
	/** 二级字库偏移量 */
	static int ioffset = 0;
	/** 存放所有国标二级汉字读音 */
	static final String LS_SECONDSECTABLE = "CJWGNSPGCGNE[Y[BTYYZDXYKYGT[JNNJQMBSGZSCYJSYY" + "[PGKBZGY[YWJKGKLJYWKPJQHY[W[DZLSGMRYPYWWCCKZNKYYGTTNJJNYKKZYTCJNMCYLQLYPYQFQRPZSLWBTGKJFYXJWZLTBNCXJJJJTXDTTSQZYCDXXHGCK" + "[PHFFSS[YBGXLPPBYLL[HLXS[ZM[JHSOJNGHDZQYKLGJHSGQZHXQGKEZZWYSCSCJXYEYXADZPMDSSMZJZQJYZC[J" + "[WQJBYZPXGZNZCPWHKXHQKMWFBPBYDTJZZKQHYLYGXFPTYJYYZPSZLFCHMQSHGMXXSXJ[" + "[DCSBBQBEFSJYHXWGZKPYLQBGLDLCCTNMAYDDKSSNGYCSGXLYZAYBNPTSDKDYLHGYMYLCXPY" + "[JNDQJWXQXFYYFJLEJPZRXCCQWQQSBNKYMGPLBMJRQCFLNYMYQMSQYRBCJTHZTQFRXQHXMJJCJLXQGJMSHZKBSWYEMYLTXFSYDSWLYCJQXSJNQBSCTYHBFTDCYZDJWY" + "GHQFRXWCKQKXEBPTLPXJZSRMEBWHJLBJSLYYSMDXLCLQKXLHXJRZJMFQHXHWYWSBHTRXXGLHQHFNM[YKLDYXZPYLGG[MTCFPAJJZYLJTYANJGBJPLQGDZYQY" + "AXBKYSECJSZNSLYZHSXLZCGHPXZHZNYTDSBCJKDLZAYFMYDLEBBGQYZKXGLDNDNYSKJSHDLYXBCGHXYPKDJMMZNGMMCLGWZSZXZJFZNMLZZTHCSYDBDLLSCDD"
			+ "NLKJYKJSYCJLKWHQASDKNHCSGANHDAASHTCPLCPQYBSDMPJLPZJOQLCDHJJYSPRCHN[NNLHLYYQYHWZPTCZGWWMZFFJQQQQYXACLBHKDJXDGMMYDJXZLLSYGX" + "GKJRYWZWYCLZMSSJZLDBYD[FCXYHLXCHYZJQ[[QAGMNYXPFRKSSBJLYXYSYGLNSCMHZWWMNZJJLXXHCHSY[[TTXRYCYXBYHCSMXJSZNPWGPXXTAYBGAJCXLY" + "[DCCWZOCWKCCSBNHCPDYZNFCYYTYCKXKYBSQKKYTQQXFCWCHCYKELZQBSQYJQCCLMTHSYWHMKTLKJLYCXWHEQQHTQH[PQ" + "[QSCFYMNDMGBWHWLGSLLYSDLMLXPTHMJHWLJZYHZJXHTXJLHXRSWLWZJCBXMHZQXSDZPMGFCSGLSXYMJSHXPJXWMYQKSMYPLRTHBXFTPMHYXLCHLHLZY" + "LXGSSSSTCLSLDCLRPBHZHXYYFHB[GDMYCNQQWLQHJJ[YWJZYEJJDHPBLQXTQKWHLCHQXAGTLXLJXMSL[HTZKZJECXJCJNMFBY[SFYWYBJZGNYSDZSQYRSLJ" + "PCLPWXSDWEJBJCBCNAYTWGMPAPCLYQPCLZXSBNMSGGFNZJJBZSFZYNDXHPLQKZCZWALSBCCJX[YZGWKYPSGXFZFCDKHJGXDLQFSGDSLQWZKXTMHSBGZMJZRGLYJ" + "BPMLMSXLZJQQHZYJCZYDJWBMYKLDDPMJEGXYHYLXHLQYQHKYCWCJMYYXNATJHYCCXZPCQLBZWWYTWBQCMLPMYRJCCCXFPZNZZLJPLXXYZTZLGDLDCKLYRZZGQTG"
			+ "JHHGJLJAXFGFJZSLCFDQZLCLGJDJCSNZLLJPJQDCCLCJXMYZFTSXGCGSBRZXJQQCTZHGYQTJQQLZXJYLYLBCYAMCSTYLPDJBYREGKLZYZHLYSZQLZNWCZCLLWJQ" + "JJJKDGJZOLBBZPPGLGHTGZXYGHZMYCNQSYCYHBHGXKAMTXYXNBSKYZZGJZLQJDFCJXDYGJQJJPMGWGJJJPKQSBGBMMCJSSCLPQPDXCDYYKY[CJDDYYGYWRHJRTGZ" + "NYQLDKLJSZZGZQZJGDYKSHPZMTLCPWNJAFYZDJCNMWESCYGLBTZCGMSSLLYXQSXSBSJSBBSGGHFJLYPMZJNLYYWDQSHZXTYYWHMZYHYWDBXBTLMSYYYFSXJC[DXX" + "LHJHF[SXZQHFZMZCZTQCXZXRTTDJHNNYZQQMNQDMMG[YDXMJGDHCDYZBFFALLZTDLTFXMXQZDNGWQDBDCZJDXBZGSQQDDJCMBKZFFXMKDMDSYYSZCMLJDSYNSBRS" + "KMKMPCKLGDBQTFZSWTFGGLYPLLJZHGJ[GYPZLTCSMCNBTJBQFKTHBYZGKPBBYMTDSSXTBNPDKLEYCJNYDDYKZDDHQHSDZSCTARLLTKZLGECLLKJLQJAQNBDKKGHP" + "JTZQKSECSHALQFMMGJNLYJBBTMLYZXDCJPLDLPCQDHZYCBZSCZBZMSLJFLKRZJSNFRGJHXPDHYJYBZGDLQCSEZGXLBLGYXTWMABCHECMWYJYZLLJJYHLG[DJLSLY"
			+ "GKDZPZXJYYZLWCXSZFGWYYDLYHCLJSCMBJHBLYZLYCBLYDPDQYSXQZBYTDKYXJY[CNRJMPDJGKLCLJBCTBJDDBBLBLCZQRPPXJCJLZCSHLTOLJNMDDDLNGKAQHQH" + "JGYKHEZNMSHRP[QQJCHGMFPRXHJGDYCHGHLYRZQLCYQJNZSQTKQJYMSZSWLCFQQQXYFGGYPTQWLMCRNFKKFSYYLQBMQAMMMYXCTPSHCPTXXZZSMPHPSHMCLMLDQF" + "YQXSZYYDYJZZHQPDSZGLSTJBCKBXYQZJSGPSXQZQZRQTBDKYXZKHHGFLBCSMDLDGDZDBLZYYCXNNCSYBZBFGLZZXSWMSCCMQNJQSBDQSJTXXMBLTXZCLZSHZCXRQ" + "JGJYLXZFJPHYMZQQYDFQJJLZZNZJCDGZYGCTXMZYSCTLKPHTXHTLBJXJLXSCDQXCBBTJFQZFSLTJBTKQBXXJJLJCHCZDBZJDCZJDCPRNPQCJPFCZLCLZXZDMXMPH" + "JSGZGSZZQLYLWTJPFSYASMCJBTZKYCWMYTCSJJLJCQLWZMALBXYFBPNLSFHTGJWEJJXXGLLJSTGSHJQLZFKCGNNNSZFDEQFHBSAQTGYLBXMMYGSZLDYDQMJJRGBJ" + "TKGDHGKBLQKBDMBYLXWCXYTTYBKMRTJZXQJBHLMHMJJZMQASLDCYXYQDLQCAFYWYXQHZ";

	public static Map<String, String> polyphoneMap = null;

	static {
//		if (polyphoneMap == null) {
//			polyphoneMap = new HashMap<String, String>();
//			Properties prop = new Properties();
//			Reader reader = null;
//			try {
//				//LogUtil.info("Start Loading property file \t");
//				reader = new InputStreamReader(new FileInputStream(Config.getClassesPath() + "PolyphoneConfig.properties"), "UTF-8");
//				prop.load(reader);
//				Iterator<Entry<Object, Object>> it = prop.entrySet().iterator();
//				while (it.hasNext()) {
//					Entry<Object, Object> entry = it.next();
//					polyphoneMap.put(entry.getKey().toString(), entry.getValue().toString());
//				}
//				//LogUtil.info("Load property file success!\t");
//			} catch (IOException e) {
//				//LogUtil.error("Could not load property file:" + e);
//			} finally {
//				if (null != reader) {
//					try {
//						reader.close();
//					} catch (IOException e) {
//					}
//				}
//			}
//		}
	}

	// 获取一个字符串的拼音码
	public static String getFirstLetter(String oriStr) {
		String sreturn = "";
		for (int j = 0; j < oriStr.length(); j++) {
			String stemp = oriStr.substring(j, j + 1);
			byte[] by = new String(stemp).getBytes(Charset.forName("GB2312"));
			if (by.length == 1) {
				sreturn = sreturn + stemp;
			} else {
				int ia = 96 + (int) by[0]; // 区码
				int ib = 96 + (int) by[1]; // 位码
				int in = ia * 100 + ib;
				if (in > 1600 && in < 5590) {
					for (int i = 0; i < 24; i++) {
						if (in < LI_SECPOSVALUE[i]) {
							sreturn = sreturn + LC_FIRSTLETTER[i - 1];
							break;
						}
					}
				} else {
					ioffset = (ia - 56) * 94 + ib - 1;
					if (ioffset >= 0 && ioffset <= 3007) {
						sreturn = sreturn + LS_SECONDSECTABLE.substring(ioffset, ioffset + 1);
					} else {
						sreturn = sreturn + stemp;
					}
				}
			}
			sreturn = sreturn.toUpperCase();
		}
		if(polyphoneMap!=null){
			for (String key : polyphoneMap.keySet()) {
				if(oriStr.contains(key)){
					try {
						Integer startIndexInteger = oriStr.indexOf(key);
						Integer endIndexInteger = startIndexInteger + key.length();
						sreturn = sreturn.substring(0, startIndexInteger) + polyphoneMap.get(key) + sreturn.substring(endIndexInteger, sreturn.length());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return sreturn;
	}
	
	public static boolean hasChinese(String queryName){
		if(StringUtils.isEmpty(queryName)){
			return false;
		}
		else{
			for (int j = 0; j < queryName.length(); j++) {
				String stemp = queryName.substring(j, j + 1);
				byte[] by = new String(stemp).getBytes(Charset.forName("GB2312"));
				if (by.length == 1) {
				}
				else{
					return true;
				}
			}
			return false;
		}
	}
	
}
