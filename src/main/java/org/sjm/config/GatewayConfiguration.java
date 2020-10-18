package org.sjm.config;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import io.micrometer.core.instrument.util.IOUtils;



@Configuration
@EnableResourceServer
public class GatewayConfiguration extends ResourceServerConfigurerAdapter {
	

	
	
    @Autowired
    private CustomAccessTokenConverter customAccessTokenConverter;
	
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/oauth/**").permitAll().antMatchers("/**")
				.access("hasAuthority('ROLE_USER') and #oauth2.hasScope('read')");

//            authenticated();
	}
	
	/*
	 * @Bean public JwtAccessTokenConverter accessTokenConverter() {
	 * JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	 * //converter.setSigningKey("secret"); converter.setVerifierKey(publicKey);
	 * return converter; }
	 */
//	
//	 @Bean
//	    public JwtAccessTokenConverter accessTokenConverter() {
//	        //final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//	        //converter.setAccessTokenConverter(customAccessTokenConverter);
//	        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//	        //converter.setSigningKey("123");
//	        //converter.setSigningKey("MIIBIjANgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkQDFUP/1yYqu5BMnvUEZmEXpdmSvG6tOroTZRb0KLIQTQJrFkyPnAM3iRa6AdWa69rDwk/2cPUZZwggHxxUftkqpqToKP1xXCKPsLTZcVERqZ4teY1XV03EB7l300SEONwb9sbb+P8rYTz9tJdzd7oh/KjDe1mhtB/ncILNAi6Szqk6YLbIsdNck44wzG7kxyfuFX5/5H5YDpa6dXJ9imV/lTE0BQVQ181nOndJwEraD/kd9aQ5xkG38tvm0d9qa0BT5eBs5wCi4jmiSSTZsV9FIYIAM08z5fOLaw+QEEyWuhe7MkeG22goFntOvFC3W7vd9GC6K9VqrahJHGWqciQIDAQAB");
//	        
//	        System.out.println("hello jwt");
//	        final ClassPathResource resource = new ClassPathResource("public.txt");
//	         String publicKey = null;
//	         try {
//	         publicKey = IOUtils.toString(resource.getInputStream());
//	         } catch (final IOException e) {
//	        throw new RuntimeException(e);
//	        }
//	         converter.setVerifierKey(publicKey);
//	         
//	         System.out.println("Set JWT signing key to: {}"+ converter.getKey());
//	        return converter;
//	    }
	
	
	/*
	 * @Bean public TokenStore tokenStore() { return new
	 * JwtTokenStore(accessTokenConverter()); }
	 */
//	    @Bean
//	    public JwtAccessTokenConverter accessTokenConverter() {
//	        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//	        converter.setSigningKey(signKey);
//	        return converter;
//	    }

	    @Bean
	    public JwtAccessTokenConverter accessTokenConverter() {
	        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	        Resource resource = new ClassPathResource("publichy.txt");
	        String publicKey = null;
	        try {
	            publicKey = IOUtils.toString(resource.getInputStream());
	        } catch (final IOException e) {
	            throw new RuntimeException(e);
	        }
	        converter.setVerifierKey(publicKey);
	        return converter;
	    }
	    
	    @Override
	    public void configure(final ResourceServerSecurityConfigurer config) {
	        config.tokenServices(tokenServices());
	    }

	    @Bean
	    public TokenStore tokenStore() {
	        return new JwtTokenStore(accessTokenConverter());
	    }

	    
	    @Bean
	    @Primary
	    public DefaultTokenServices tokenServices() {
	        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	        defaultTokenServices.setTokenStore(tokenStore());
	        return defaultTokenServices;
	    }
        // String publicKey = null;
        // try {
        // publicKey = IOUtils.toString(resource.getInputStream());
        // } catch (final IOException e) {
        // throw new RuntimeException(e);
        // }
        // converter.setVerifierKey(publicKey);
}



