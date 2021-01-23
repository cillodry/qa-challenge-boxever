package com.boxever.test;

import com.boxever.DTO.FileDTO;
import com.boxever.DTO.GistDTO;
import com.boxever.dataProvider.ConfigReader;
import com.google.gson.Gson;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class CreateAndDeleteGist {

    @Test(priority = 1)
    public void createGist() {
        FileDTO file = new FileDTO();
        HashMap<String, String> container = new HashMap<>();
        String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean risus mauris, maximus at imperdiet vel, fringilla sit amet diam. Donec tincidunt massa sed quam euismod semper. Vivamus accumsan sit amet dui imperdiet ultricies. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam tempor molestie purus id vulputate. Morbi vitae metus ipsum. Donec nunc lacus, volutpat id aliquet non, tincidunt ut nisl. Nam in accumsan odio. Praesent malesuada euismod nulla, at convallis tellus consectetur a. Curabitur iaculis faucibus dolor eleifend vestibulum. Maecenas tincidunt tortor vitae semper placerat. Curabitur facilisis nibh at urna vehicula, id scelerisque urna sollicitudin. Nulla dignissim nibh mauris, vitae luctus ligula placerat elementum. Praesent et augue est.";
        container.put("content", loremIpsum);
        file.setContainer(container);

        GistDTO gist = new GistDTO();
        gist.setDescription("GistDTO Example creation");
        gist.setFiles(file);
        gist.setPublic(false);

        Gson gson = new Gson();
        String json = gson.toJson(gist);

        given().contentType("application/json")
                .baseUri(ConfigReader.getInstance().getBaseUrl())
                .header("Authorization", "token" + " " + ConfigReader.getInstance().getToken())
                .body(json)
                .when().post("/gists");
    }

    @Test(priority = 2)
    public void retrieveAndCancelGists() {

        String gistsJsonResponse = given()
                .baseUri(ConfigReader.getInstance().getBaseUrl())
                .header("Authorization", "token" + " " + ConfigReader.getInstance().getToken())
                .when().get("/gists").asPrettyString();

        GistDTO[] jsonRetrieved = new Gson().fromJson(gistsJsonResponse, GistDTO[].class);

        ArrayList<GistDTO> gistListRetrieved = new ArrayList<>(Arrays.asList(jsonRetrieved));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        gistListRetrieved.sort(Comparator.comparing(gistTest -> (LocalDateTime.parse(gistTest.getCreated_at(), formatter))));

        GistDTO lastFromTheList = gistListRetrieved.get(gistListRetrieved.size() - 1);

        deleteGist(lastFromTheList);

    }

    private void deleteGist(GistDTO lastFromTheList) {
        String lftlID = lastFromTheList.getId();

        given().baseUri(ConfigReader.getInstance().getBaseUrl())
                .header("Authorization", "token" + " " + ConfigReader.getInstance().getToken())
                .when().delete("/gists/" + lftlID);
    }

}
