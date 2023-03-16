import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author arvin
 * @date 2023-03-16 20:51
 **/
@RunWith(SpringRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml", "file:src/main/webapp/WEB-INF/mbank-servlet.xml"})
@WebAppConfiguration
public class HelloTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testHello() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/hello"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertTrue(content.contains("hello"));
    }

    @Test
    public void testRequestUrls() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/getAllUrl"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertTrue(content.contains("getAllUrl"));
    }

}
