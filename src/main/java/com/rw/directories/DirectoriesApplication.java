package com.rw.directories;

import com.uber.jaeger.Configuration;
import com.uber.jaeger.samplers.ProbabilisticSampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import javax.servlet.Filter;

@SpringBootApplication
public class DirectoriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DirectoriesApplication.class, args);
	}

	@Bean
	public Filter filter() {
		ShallowEtagHeaderFilter filter = new ShallowEtagHeaderFilter();
		return filter;
	}


	@Bean
	public io.opentracing.Tracer jaegerTracer() {
		return new Configuration("spring-boot", new Configuration.SamplerConfiguration(ProbabilisticSampler.TYPE, 1),
				new Configuration.ReporterConfiguration())
				.getTracer();
	}
}