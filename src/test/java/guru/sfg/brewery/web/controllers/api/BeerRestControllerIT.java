package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Jeff Stark on 11/1/2020
 */

//@WebMvcTest //this only brings up a web context and will exclude anything like JpsUserDetailsService that depends on a database
@SpringBootTest //brings up a full Spring context
public class BeerRestControllerIT extends BaseIT {

    @Test
    void findBeers() throws Exception {
        mockMvc.perform(get("/api/v1//beer"))
                .andExpect(status().isOk());

    }

    @Test
    void findBeerById() throws Exception {
        mockMvc.perform(get("/api/v1//beer/97df0c39-90c4-4ae0-b663-453e8e19c311"))
                .andExpect(status().isOk());

    }

    @Test
    void findBeerByUpc() throws Exception {
        mockMvc.perform(get("/api/v1//beerUpc/0631234200036"))
                .andExpect(status().isOk());

    }

    @Test
    void deleteBeerBadCreds() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                .header("Api-Key", "spring").header("Api-Secret","guruXXXX"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                .header("Api-Key", "spring").header("Api-Secret","guru"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerHttpBasic() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteBeerNoAuth() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteBeerWithUrlParamCreds() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                .param("apiKey", "spring").param("apiSecret", "guru"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerWithBadUrlParamCreds() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                .param("apiKey", "spring").param("apiSecret", "guruXXXX"))
                .andExpect(status().isUnauthorized());
    }
}
