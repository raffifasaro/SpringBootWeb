package amogus.example.springweb;

import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReactController {

	@GetMapping("/test")
	public String index() {
		return "index";
	}
}
