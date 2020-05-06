package com.example.springtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@EnableJpaRepositories
@SpringBootApplication
//@EnableCaching
public class SpringtestApplication   {// ApplicationRunner { //WebMvcConfigurer

    public static void main(String[] args) {
        SpringApplication.run(SpringtestApplication.class, args);
    }

//    @Bean
//    public Hibernate5Module hibernate5Module(){
//        return new Hibernate5Module();
//    }
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(){
//        return builder->{ builder.indentOutput(true);
//        builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai")) ;};
//    }
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new PerformanceInteceptor())
//                .addPathPatterns("/coffee/**").addPathPatterns("/order/**");
//    }

//    @Bean
//    public Hibernate5Module hibernate5Module() {
//        return new Hibernate5Module();
//    }

    //    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jacksonBuilderCustomizer() {
//        return builder -> {
//            builder.indentOutput(true);
//            builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//        };
//    }
//    @Autowired
//    private WebClient webClient;
//
//    @Bean
//    public WebClient webClient(WebClient.Builder builder) {
//        return builder.baseUrl("http://localhost:8080").build();
//    }

//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        CountDownLatch countDownLatch = new CountDownLatch(2);
//        webClient.get()
//                .uri("/coffee/{id}", 1)
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(Coffee.class)
//                .doOnError(t -> {
//                    log.error("Error {}", t);
//                })
//                .doFinally(s -> countDownLatch.countDown())
//                .subscribeOn(Schedulers.single())
//                .subscribe(t -> log.info("coffee:", t));
//        Mono<Coffee> americano = Mono.just(
//                Coffee.builder()
//                        .name("americano")
//                        .price(25.00)
//                        .build()
//        );
//        webClient.post()
//                .uri("/coffee/")
//                .body(americano, Coffee.class)
//                .retrieve()
//                .bodyToMono(Coffee.class)
//                .doFinally(s -> countDownLatch.countDown())
//                .subscribeOn(Schedulers.single())
//                .subscribe(c -> log.info("Coffee Created: {}", c));
//
//        countDownLatch.await();
//
////
////        configSetings result=  getsetting() ;
////       new ArrayList<>(Arrays.asList(result,result));
////        System.out.println(result.getName());
//    }
//    @Bean
//    @ConfigurationProperties("setting")
//    public configSetings getsetting(){
//        return new configSetings();
//    }


}
