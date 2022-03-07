package com.example.taskworklife.kamer;

import com.example.taskworklife.config.ReserveringConfiguration;
import com.example.taskworklife.config.WebConfiguration;
import com.example.taskworklife.controller.KamerController;
import com.example.taskworklife.exception.kamer.KamerIsNietGevonden;
import com.example.taskworklife.models.Kamer;
import com.example.taskworklife.service.kamer.KamerService;
import com.example.taskworklife.service.kamer.KamerServiceImpl;
import com.example.taskworklife.service.user.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = KamerController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class KamerControllerTest {

    @MockBean
    private KamerService kamerService;
    @MockBean
    UserServiceImpl userService;

    @MockBean
    private KamerController kamerController;

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
        mockMvc.perform(MockMvcRequestBuilders.get("/kamer/all").param("pageNo", kamerTestHelper.standaardPaginaNummer).param("pageSize", kamerTestHelper.standaardPaginaSize).param("sort",kamerTestHelper.standaardSort)).andExpect(MockMvcResultMatchers.status().is(200)).andExpect(jsonPath("$").doesNotExist());

        when(kamerService.getKamers(0,10,"kamer")).thenReturn(new PageImpl<Kamer>(kamerTestHelper.krijgKamers()));

//        when(kamerController.getKamers(Integer.parseInt(kamerTestHelper.standaardPaginaNummer),Integer.parseInt(kamerTestHelper.standaardPaginaSize),kamerTestHelper.standaardSort)).thenReturn(new ResponseEntity<Page<Kamer>>(new PageImpl<Kamer>(kamerTestHelper.krijgKamers()), HttpStatus.OK));

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
//        requestParams.add("pageNo", "0");
//        requestParams.add("pageSize", "10");
//        requestParams.add("sortBy", "kamer");
////
////        mockMvc.perform(MockMvcRequestBuilders.get("/kamer/all").params(requestParams)).andExpect(MockMvcResultMatchers.status().is(200));
//        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/kamer/all").params(requestParams));
//        System.out.println(perform);
    }

    @Test
    void getKamerMetNaam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/kamer/{kamerNaam}", "kamerNaam")).andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof KamerIsNietGevonden));


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