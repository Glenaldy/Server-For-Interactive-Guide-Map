package edu.kcg.glenaldy.masterproject4_server.config

import edu.kcg.glenaldy.masterproject4_server.MasterProject4ServerApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

class ServletInitializer : SpringBootServletInitializer() {

	override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
		return application.sources(MasterProject4ServerApplication::class.java)
	}

}
