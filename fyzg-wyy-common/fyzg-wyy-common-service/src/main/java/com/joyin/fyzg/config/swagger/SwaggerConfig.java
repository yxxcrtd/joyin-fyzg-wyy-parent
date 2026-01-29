package com.joyin.fyzg.config.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 * <br/>
 * @author pengzhen
 * @date 2019/7/23 0023 上午 9:45
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Autowired
	private SwaggerInfo swaggerInfo;

	@Bean
	public Docket controllerApi() {

		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.groupName(swaggerInfo.getGroupName())
				.enable(swaggerInfo.isSwaggerShow())
				.apiInfo(apiInfo());
		ApiSelectorBuilder builder = docket.select();
		if (!StringUtils.isEmpty(swaggerInfo.getBasePackage())) {
			builder = builder.apis(RequestHandlerSelectors.basePackage(swaggerInfo.getBasePackage()));
		}
		if (!StringUtils.isEmpty(swaggerInfo.getAntPath())) {
			builder = builder.paths(PathSelectors.ant(swaggerInfo.getAntPath()));
		}

		return builder.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				//设置文档的标题
				.title(swaggerInfo.getTitle())
				//设置文档的描述
				.description(swaggerInfo.getDescription())
				//设置文档的License信息
				.termsOfServiceUrl("http://springfox.io")
				.license(swaggerInfo.getLicense())
				.licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
				//设置文档的版本信息
				.version("2.0")
				.build();
	}

}
