package com.github.luismoramedina.simplegateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@EnableZuulProxy
@RestController
public class SimpleGatewayApplication {

	private String HTMLHEADER = "<!DOCTYPE html><html><head><title>Gateway apis</title></head><body>";
	private String HTMLFOOTER = "</body></html>";

	@Value("${swagger.url.suffix:/swagger-ui.html}")
	private String swaggerSuffix;

	@Autowired
	private RouteLocator routeLocator;

	private String apisHtml;

	public static void main(String[] args) {
		SpringApplication.run(SimpleGatewayApplication.class, args);
	}

	@RequestMapping(value = "/console/apis", method = RequestMethod.GET)
	public String apis() {
		return apisHtml;
	}

	@PostConstruct
	public void init(){
		List<Route> routes;
		routes = routeLocator.getRoutes();
		String body = "";
		for (Route route : routes) {
			System.out.println("route = " + route);
			String location = route.getLocation() + swaggerSuffix;
			System.out.println("location = " + location);
			body = body.concat("<br>" + "<a href=\"" + location + "\">" + route.getId() + "</a>");
		}
		apisHtml = HTMLHEADER + body + HTMLFOOTER;
	}

}
