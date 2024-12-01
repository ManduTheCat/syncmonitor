package syncMonitor.config;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class MonitorConfig {
    private ConfigWrapper configWrapper; // 최상위 YAML 데이터를 담는 객체

    public MonitorConfig() {
        String defaultYmlFileName = "config.yml"; // 기본 YAML 파일 이름
        String customYmlPath = System.getProperty("config.file"); // 전달된 경로
        Yaml yaml = new Yaml();

        try {
            File ymlFile;

            if (customYmlPath != null) {
                ymlFile = new File(customYmlPath);
                if (!ymlFile.exists()) {
                    throw new RuntimeException("전달된 YAML 파일을 찾을 수 없습니다: " + customYmlPath);
                }
                System.out.println("전달된 파일에서 YAML 로드: " + ymlFile.getAbsolutePath());
            } else {
                String currentDir = System.getProperty("user.dir");
                ymlFile = new File(currentDir, defaultYmlFileName);
                if (!ymlFile.exists()) {
                    throw new RuntimeException("기본 YAML 파일을 찾을 수 없습니다: " + ymlFile.getAbsolutePath());
                }
                System.out.println("기본 파일에서 YAML 로드: " + ymlFile.getAbsolutePath());
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

    public TiberoConfig getTiberoConfig() {
        return configWrapper.getDatabase().getTibero();
    }

    public OracleConfig getOracleConfig() {
        return configWrapper.getDatabase().getOracle();
    }

    // 최상위 YAML 키를 매핑하는 클래스
    public static class ConfigWrapper {
        private DatabaseConfig database;

        public DatabaseConfig getDatabase() {
            return database;
        }

        public void setDatabase(DatabaseConfig database) {
            this.database = database;
        }
    }

    public static class DatabaseConfig {
        private TiberoConfig tibero;
        private OracleConfig oracle;

        public TiberoConfig getTibero() {
            return tibero;
        }

        public void setTibero(TiberoConfig tibero) {
            this.tibero = tibero;
        }

        public OracleConfig getOracle() {
            return oracle;
        }

        public void setOracle(OracleConfig oracle) {
            this.oracle = oracle;
        }
    }

}
