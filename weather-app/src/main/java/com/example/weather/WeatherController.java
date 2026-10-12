package com.example.weather;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

@Controller
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    @SuppressWarnings("unchecked")
    public String home(@RequestParam(defaultValue = "Mumbai") String city, Model model) {
        model.addAttribute("query", city);
        try {
            Map<String, Object> data = weatherService.getWeather(city);
            Map<String, Object> main = (Map<String, Object>) data.get("main");
            Map<String, Object> wind = (Map<String, Object>) data.get("wind");
            Map<String, Object> sys = (Map<String, Object>) data.get("sys");
            List<Map<String, Object>> weather = (List<Map<String, Object>>) data.get("weather");

            model.addAttribute("city", data.get("name"));
            model.addAttribute("country", sys.get("country"));
            model.addAttribute("temp", main.get("temp"));
            model.addAttribute("feelsLike", main.get("feels_like"));
            model.addAttribute("humidity", main.get("humidity"));
            model.addAttribute("pressure", main.get("pressure"));
            model.addAttribute("windSpeed", wind.get("speed"));
            model.addAttribute("description", weather.get(0).get("description"));
            model.addAttribute("icon", weather.get(0).get("icon"));
        } catch (HttpClientErrorException e) {
            HttpStatusCode status = e.getStatusCode();
            if (status.value() == 404) {
                model.addAttribute("error", "City not found. Check the spelling.");
            } else if (status.value() == 401) {
                model.addAttribute("error", "Invalid API key (new keys can take up to 2 hours to activate).");
            } else {
                model.addAttribute("error", "Weather API error: " + status.value());
            }
        } catch (Exception e) {
            model.addAttribute("error", "Something went wrong: " + e.getMessage());
        }
        return "index";
    }

    @GetMapping("/api/weather")
    @ResponseBody
    public Map<String, Object> api(@RequestParam String city) {
        return weatherService.getWeather(city);
    }
}
