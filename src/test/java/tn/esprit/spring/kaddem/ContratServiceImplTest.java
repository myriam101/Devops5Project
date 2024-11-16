package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratServiceImplTest {

    @Mock
    ContratRepository contratRepository;

    @InjectMocks
    ContratServiceImpl contratService;

    private Contrat contrat;

    @BeforeEach
    void setUp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateDebut = dateFormat.parse("2023-10-01");
            Date dateFin = dateFormat.parse("2024-10-01");

            contrat = new Contrat();
            contrat.setIdContrat(1);
            contrat.setDateDebutContrat(dateDebut);
            contrat.setDateFinContrat(dateFin);
            contrat.setSpecialite(Specialite.CLOUD);
            contrat.setArchive(false);
            contrat.setMontantContrat(2000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void retrieveAllContratsTest() {
        List<Contrat> contratsList = new ArrayList<>();
        contratsList.add(contrat);

        when(contratRepository.findAll()).thenReturn(contratsList);

        List<Contrat> retrievedContratsList = contratService.retrieveAllContrats();

        assertThat(retrievedContratsList).hasSize(1);
        verify(contratRepository).findAll();
    }

    @Test
    void addContratTest() {
        when(contratRepository.save(Mockito.any(Contrat.class))).thenReturn(contrat);

        Contrat savedContrat = contratService.addContrat(contrat);

        assertThat(savedContrat).isNotNull();
        verify(contratRepository).save(Mockito.any(Contrat.class));
    }


    @Test
    void updateContratTest() {
        when(contratRepository.save(contrat)).thenReturn(contrat);

        contrat.setMontantContrat(2500);

        Contrat updatedContrat = contratService.updateContrat(contrat);

        assertThat(updatedContrat).isNotNull();
        assertThat(updatedContrat.getMontantContrat()).isEqualTo(2500);
        verify(contratRepository).save(contrat);
    }

    @Test
    void retrieveContratTest() {
        Integer contratId = 1;
        when(contratRepository.findById(contratId)).thenReturn(Optional.of(contrat));

        Contrat retrievedContrat = contratService.retrieveContrat(contratId);

        assertThat(retrievedContrat).isNotNull();
        verify(contratRepository).findById(contratId);
    }
}
