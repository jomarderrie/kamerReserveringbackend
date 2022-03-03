package com.example.taskworklife.kamer;

import com.example.taskworklife.config.ReserveringConfiguration;
import com.example.taskworklife.config.WebConfiguration;
import com.example.taskworklife.controller.KamerController;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.service.kamer.KamerService;
import com.example.taskworklife.service.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = KamerController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class KamerControllerTest {

    @MockBean
    private KamerService kamerService;
    @MockBean
    UserServiceImpl userService;

    @MockBean
    ReserveringConfiguration reserveringConfiguration;
@MockBean
    WebConfiguration webConfiguration;
    KamerTestHelper kamerTestHelper = new KamerTestHelper();
    @Autowired
    private MockMvc mockMvc;
    String beginKamerEndPoint = "/kamer";
    String getKamersUrl = beginKamerEndPoint+"/all";
    String getKamerMetNaamUrl = beginKamerEndPoint+"/{kamerNaam}";
    String postMaakNieuweKamerAanUrl = beginKamerEndPoint+"/new";
    String getAllKamersMetEenBepaaldeNaamOpEenBepaaldeDatumUrl = beginKamerEndPoint+"/{kamerNaam}/reserveringen/{datum}";


    @Test
    void getKamers() throws Exception {
        // begin staat
        mockMvc.perform(MockMvcRequestBuilders.get("/kamer/all").param("pageNo", kamerTestHelper.standaardPaginaNummer).param("pageSize", kamerTestHelper.standaardPaginaSize).param("sort",kamerTestHelper.standaardSort)).andExpect(MockMvcResultMatchers.status().is(200)).andExpect(MockMvcResultMatchers.content().equals());

        when(kamerService.getKamers(0,10,"kamer")).thenReturn(new PageImpl<Kamer>(kamerTestHelper.krijgKamers()));

        mockMvc.perform(MockMvcRequestBuilders.get("/kamer/all").param("pageNo", kamerTestHelper.standaardPaginaNummer).param("pageSize", kamerTestHelper.standaardPaginaSize).param("sort",kamerTestHelper.standaardSort));


    }

    @Test
    void getKamerMetNaam() {

    }

    @Test
    void maakNieuweKamerAan() {
//        mockMvc.perform(post("/new").r)
    }

    @Test
    void getAllKamersMetEenBepaaldeNaamOpEenBepaaldeDatum() {
    }

    @Test
    void editKamer() {
    }

    @Test
    void verwijderKamer() {
    }

    @Test
    void reserveerKamer() {
    }

    @Test
    void getLoggedInUser() {
    }
}