package ro.microservices.inventory.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

@Configuration
public class JwtConfiguration {

	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;

	@Bean
	@Qualifier("tokenStore")
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter);

	}

	@Bean
	protected JwtAccessTokenConverter jwtTokenEnhancer() {
		Resource resource = new ClassPathResource("public.cert");
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

		try {
			String publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
			converter.setVerifierKey(publicKey);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return converter;
	}

}
