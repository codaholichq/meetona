package meetona.cell;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class CellRunner implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private final CellRepository cellRepository;

    @Override
    public void run(String... args) throws Exception {
        boolean existsCells = cellRepository.findAll().isEmpty();

        if (existsCells) {
            String githubRepoUrl = "https://raw.githubusercontent.com/codaholichq/data/main/cells.json";
            String jsonData = restTemplate.getForObject(githubRepoUrl, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Cell[] cells = objectMapper.readValue(jsonData, Cell[].class);

            cellRepository.saveAll(Arrays.asList(cells));
            log.info("Seeded cells from GitHub repository.");
        }
    }
}
