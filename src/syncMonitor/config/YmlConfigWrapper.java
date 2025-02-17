package syncMonitor.config;


import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import syncMonitor.config.wrapper.ConfigWrapper;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Slf4j
public class YmlConfigWrapper {

    private final ConfigWrapper configWrapper; // 최상위 YAML 데이터를 담는 객체



    public YmlConfigWrapper() {
        String defaultYmlFileName = "config.yml"; // 기본 YAML 파일 이름
        String customYmlPath = System.getProperty("config.file"); // 전달된 경로
        Yaml yaml = new Yaml();

        try {
            File ymlFile;

            if (customYmlPath != null) {
                ymlFile = new File(customYmlPath);
                if (!ymlFile.exists()) {
                    throw new RuntimeException("내부 YAML 파일을 찾을 수 없습니다: " + customYmlPath);
                }
                log.info("전달된 파일에서 YAML 로드: " + ymlFile.getAbsolutePath());
            } else {
                String currentDir = System.getProperty("user.dir");
                ymlFile = new File(currentDir, defaultYmlFileName);
                if (!ymlFile.exists()) {
                    throw new RuntimeException("기본 YAML 파일을 찾을 수 없습니다: " + ymlFile.getAbsolutePath());
                }
                log.info("기본 파일에서 YAML 로드: " + ymlFile.getAbsolutePath());
            }

            // YAML 파일 읽기 및 매핑
            try (InputStream inputStream = new FileInputStream(ymlFile)) {
                configWrapper = yaml.loadAs(inputStream, ConfigWrapper.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("YAML 파일 읽기 또는 파싱 실패: " + e.getMessage(), e);
        }
    }

    public ConfigWrapper getConfigWrapper() {
        return configWrapper;
    }
}
