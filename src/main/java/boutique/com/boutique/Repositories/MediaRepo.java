package boutique.com.boutique.Repositories;

import boutique.com.boutique.Entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepo extends JpaRepository<Media,Long> {
}
