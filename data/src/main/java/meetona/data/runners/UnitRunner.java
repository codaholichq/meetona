package meetona.data.runners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.core.entity.Unit;
import meetona.data.repository.UnitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class UnitRunner implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private final UnitRepository unitRepository;

    @Override
    public void run(String... args) throws Exception {
        boolean existsUnits = unitRepository.findAll().isEmpty();

        if (existsUnits) {
            String githubRepoUrl = "https://raw.githubusercontent.com/codaholichq/data/main/units.json";
            String jsonData = restTemplate.getForObject(githubRepoUrl, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Unit[] units = objectMapper.readValue(jsonData, Unit[].class);

            unitRepository.saveAll(Arrays.asList(units));
            log.info("Seeded units from GitHub repository.");
        }
    }
}
