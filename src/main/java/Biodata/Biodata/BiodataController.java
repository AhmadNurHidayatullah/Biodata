package Biodata.Biodata;

import Biodata.Biodata.exceptions.NonexistentEntityException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BiodataController {

    private final DatadiriJpaController datadiriJpaController;

    public BiodataController() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Biodata_Biodata_jar_0.0.1-SNAPSHOTPU");
        this.datadiriJpaController = new DatadiriJpaController(emf);
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("datadiri", new Datadiri());
        return "form";
    }
    

    @PostMapping("/simpan")
    public String simpanData(@ModelAttribute Datadiri datadiri, RedirectAttributes redirectAttributes) {
    datadiriJpaController.create(datadiri);
    redirectAttributes.addFlashAttribute("sukses", "Data berhasil disimpan!");
    return "redirect:/list";
}


    @GetMapping("/list")
    public String tampilkanData(Model model) {
        List<Datadiri> list = datadiriJpaController.findDatadiriEntities();
        model.addAttribute("dataList", list);
        return "list";
    }

    // Opsional: fitur hapus
    @GetMapping("/hapus/{id}")
    public String hapusData(@PathVariable("id") int id) {
        try {
            datadiriJpaController.destroy(id);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        }
        return "redirect:/list";
    }
    
}

