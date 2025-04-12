package com.example.rbacdemo.controller;

import com.example.rbacdemo.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@RequiredArgsConstructor
public class RegisterController {

	private final RestTemplate restTemplate;

	@Value("${app.api.base-url:http://localhost:8080}")
	private String apiBaseUrl;

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		if (!model.containsAttribute("registerRequest")) {
			model.addAttribute("registerRequest", new RegisterRequest());
		}
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute RegisterRequest registerRequest, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "register";
		}

		try {
			ResponseEntity<?> response = restTemplate.postForEntity(apiBaseUrl + "/api/auth/register", registerRequest,
					Object.class);

			if (response.getStatusCode().is2xxSuccessful()) {
				redirectAttributes.addFlashAttribute("success", "注册成功！请登录");
				return "redirect:/login";
			}
			else {
				redirectAttributes.addFlashAttribute("error", "注册失败，请稍后重试");
				redirectAttributes.addFlashAttribute("registerRequest", registerRequest);
				return "redirect:/register";
			}
		}
		catch (HttpClientErrorException e) {
			redirectAttributes.addFlashAttribute("error", "注册失败：" + e.getResponseBodyAsString());
			redirectAttributes.addFlashAttribute("registerRequest", registerRequest);
			return "redirect:/register";
		}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "注册失败：" + e.getMessage());
			redirectAttributes.addFlashAttribute("registerRequest", registerRequest);
			return "redirect:/register";
		}
	}

}