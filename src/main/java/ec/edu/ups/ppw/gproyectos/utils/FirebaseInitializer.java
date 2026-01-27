package ec.edu.ups.ppw.gproyectos.utils;

import java.io.InputStream;
import javax.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Singleton
@Startup
public class FirebaseInitializer {

	@PostConstruct
    public void init() {
        try {
            InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("firebase-key.json");

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase conectado exitosamente desde Java");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error conectando Firebase: " + e.getMessage());
        }
    }
}
