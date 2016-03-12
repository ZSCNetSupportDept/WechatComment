package love.sola.wechat.comment.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * ***********************************************
 * Created by Sola on 2016/3/9.
 * Don't modify this source without my agreement
 * ***********************************************
 */
@Configuration
@EnableSpringHttpSession
public class HttpSessionConfiguration {

	@Bean
	SessionRepository sessionRepository() {
		return new MapSessionRepository();
	}

	@Bean
	HttpSessionStrategy httpSessionStrategy() {
		return new HeaderHttpSessionStrategy();
	}

}