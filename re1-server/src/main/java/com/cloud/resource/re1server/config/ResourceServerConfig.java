package com.cloud.resource.re1server.config;

public class ResourceServerConfig /*extends ResourceServerConfigurerAdapter*/ {

   /* @Value("${spring.application.name}")
    private String RESOURCE_ID;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .and()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .httpBasic();
    }*/
}
