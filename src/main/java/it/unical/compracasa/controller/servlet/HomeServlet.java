package it.unical.compracasa.controller.servlet;

import it.unical.compracasa.persistence.DBManager;
import it.unical.compracasa.persistence.dao.PropertyDao;
import it.unical.compracasa.persistence.model.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@WebServlet("/saveAnnouncment")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isParsingImages = false;
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

        PropertyDao propertyDao = DBManager.getInstance().getPropertyDao();

        // Read JSON data from the request body as String
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        String jsonBody = requestBody.toString();
        System.out.println("Received JSON data from Express server:");
        System.out.println(jsonBody);

        try {
        JSONObject jsonObject = new JSONObject(jsonBody);

        JSONObject typeFormData = jsonObject.getJSONObject("typeFormData");
        JSONObject charFormData = jsonObject.getJSONObject("charFormData");
        JSONObject descFormData = jsonObject.getJSONObject("descriptionFormData");
        JSONArray imagesArray = null;
        if (isParsingImages) try {
            imagesArray = descFormData.getJSONArray("images");
        } catch (Exception ignored){};
        JSONObject contFormData = jsonObject.getJSONObject("contactsFormData");
        char propertyType = typeFormData.getString("propertyType").charAt(0);
        String heatingSystem = switch (charFormData.getString("warming")) {
            case "Autonomo", "autonomous" -> "Autonomo";
            case "Centralizzato", "centralized" -> "Centralizzato";
            case "Assente", "absent", "" -> "Assente";
            default -> throw new IllegalStateException("Unexpected value: " + charFormData.getString("warming"));
        };
        String sellMode = switch (typeFormData.getString("sellMode")) {
            case "rent", "Affitto" -> "Affitto";
            case "sell", "Vendita", "InVendita" -> "Vendita";
            case "", "Non Specificato" -> "Non Specificato";
            default -> throw new IllegalStateException("Unexpected value: " + charFormData.getString("sellMode"));
        };
        Property p = switch (propertyType) {
            case 'A' -> new Apartment(
                0,
                DBManager.getInstance().getUserDao().find(contFormData.getString("username")),
                sellMode + "," +
                        typeFormData.getString("address") + "," +
                        typeFormData.getString("civicNumber") + "," +
                        contFormData.getString("email") + "," +
                        contFormData.getString("phoneNumber"),
                descFormData.getString("description"),
                typeFormData.getString("town"),
                descFormData.getFloat("lat"),
                descFormData.getFloat("lng"),
                charFormData.getString("propertyState"),
                charFormData.getString("energyClass"),
                heatingSystem,
                descFormData.getString("imageLink"),
                Integer.parseInt(charFormData.getString("floor")),
                Integer.parseInt(charFormData.getString("surface")),
                Integer.parseInt(descFormData.getString("price")),
                Integer.parseInt(charFormData.getString("bedrooms")),
                Integer.parseInt(charFormData.getString("bathroom")),
                Integer.parseInt(charFormData.getString("kitchens")),
                charFormData.getBoolean("hall"),
                charFormData.getBoolean("elevator") ? 1 : 0,
                charFormData.getBoolean("carPlace") ? 1 : 0,
                charFormData.getBoolean("disableBathroom"),
                charFormData.getString("furnishedStatus").isEmpty()
            );
            case 'G' -> new Garage(
                0,
                DBManager.getInstance().getUserDao().find(contFormData.getString("username")),
                sellMode + "," +
                    typeFormData.getString("address") + "," +
                    typeFormData.getString("civicNumber") + "," +
                    contFormData.getString("email") + "," +
                    contFormData.getString("phoneNumber"),
                descFormData.getString("description"),
                typeFormData.getString("town"),
                descFormData.getFloat("lat"),
                descFormData.getFloat("lng"),
                charFormData.getString("propertyState"),
                charFormData.getString("energyClass"),
                heatingSystem,
                descFormData.getString("imageLink"),
                Integer.parseInt(charFormData.getString("floor")),
                Integer.parseInt(charFormData.getString("surface")),
                Integer.parseInt(descFormData.getString("price")),
                charFormData.getString("garageType").equals("indipendent"),
                charFormData.getInt("doorWidth"),
                charFormData.getInt("electricCharge"),
                //electricCharge
                charFormData.getBoolean("h24Accessibility"),
                charFormData.getBoolean("disableAcess"),
                charFormData.getBoolean("guards"),
                charFormData.getBoolean("alarm")
            );

            case 'O' -> new Office(
                0,
                DBManager.getInstance().getUserDao().find(contFormData.getString("username")),
                sellMode + "," +
                    typeFormData.getString("address") + "," +
                    typeFormData.getString("civicNumber") + "," +
                    contFormData.getString("email") + "," +
                    contFormData.getString("phoneNumber"),
                descFormData.getString("description"),
                typeFormData.getString("town"),
                descFormData.getFloat("lat"),
                descFormData.getFloat("lng"),
                charFormData.getString("propertyState"),
                charFormData.getString("energyClass"),
                heatingSystem,
                descFormData.getString("imageLink"),
                Integer.parseInt(charFormData.getString("floor")),
                Integer.parseInt(charFormData.getString("surface")),
                Integer.parseInt(descFormData.getString("price")),
                Integer.parseInt(charFormData.getString("bedrooms")),
                Integer.parseInt(charFormData.getString("bathroom")),
                Integer.parseInt(charFormData.getString("kitchens")),
                charFormData.getBoolean("hall"),
                charFormData.getBoolean("elevator") ? 1 : 0,
                charFormData.getBoolean("carPlace") ? 1 : 0,
                charFormData.getBoolean("disableBathroom"),
                charFormData.getString("furnishedStatus").equals("true")
            );
            case 'P', 'B' -> new Palace(
                0,
                DBManager.getInstance().getUserDao().find(contFormData.getString("username")),
                sellMode + "," +
                    typeFormData.getString("address") + "," +
                    typeFormData.getString("civicNumber") + "," +
                    contFormData.getString("email") + "," +
                    contFormData.getString("phoneNumber"),
                descFormData.getString("description"),
                typeFormData.getString("town"),
                descFormData.getFloat("lat"),
                descFormData.getFloat("lng"),
                charFormData.getString("propertyState"),
                charFormData.getString("energyClass"),
                heatingSystem,
                descFormData.getString("imageLink"),
                Integer.parseInt(charFormData.getString("floors")),
                Integer.parseInt(charFormData.getString("surface")),
                Integer.parseInt(descFormData.getString("price")),
                charFormData.getString("buildingType"),
                Integer.parseInt(charFormData.getString("elevators")),
                Integer.parseInt(charFormData.getString("bathrooms")),
                Integer.parseInt(charFormData.getString("internalCarPlaces")),
                Integer.parseInt(charFormData.getString("externalCarPlaces")),
                charFormData.getString("furnishedStatus").equals("true")
            );
            case 'S' -> new Shop(
                0,
                DBManager.getInstance().getUserDao().find(contFormData.getString("username")),
                sellMode + "," +
                    typeFormData.getString("address") + "," +
                    typeFormData.getString("civicNumber") + "," +
                    contFormData.getString("email") + "," +
                    contFormData.getString("phoneNumber"),
                descFormData.getString("description"),
                typeFormData.getString("town"),
                descFormData.getFloat("lat"),
                descFormData.getFloat("lng"),
                charFormData.getString("propertyState"),
                charFormData.getString("energyClass"),
                heatingSystem,
                descFormData.getString("imageLink"),
                Integer.parseInt(charFormData.getString("floor")),
                Integer.parseInt(charFormData.getString("surface")),
                Integer.parseInt(descFormData.getString("price")),
                charFormData.getBoolean("depot"),
                charFormData.getBoolean("square"),
                charFormData.getBoolean("office"),
                charFormData.getBoolean("garage") ? 1 : 0,
                charFormData.getBoolean("alarm"),
                Integer.parseInt(charFormData.getString("locals")),
                Integer.parseInt(charFormData.getString("shopWindows")),
                Integer.parseInt(charFormData.getString("bathrooms")),
                charFormData.getBoolean("disableBathroom"),
                charFormData.getString("furnishedStatus").equals("true"),
                charFormData.getString("shopType")
            );
            case 'V' -> new Villa(
                0,
                DBManager.getInstance().getUserDao().find(contFormData.getString("username")),
                sellMode + "," +
                    typeFormData.getString("address") + "," +
                    typeFormData.getString("civicNumber") + "," +
                    contFormData.getString("email") + "," +
                    contFormData.getString("phoneNumber"),
                descFormData.getString("description"),
                typeFormData.getString("town"),
                descFormData.getFloat("lat"),
                descFormData.getFloat("lng"),
                charFormData.getString("propertyState"),
                charFormData.getString("energyClass"),
                heatingSystem,
                descFormData.getString("imageLink"),
                Integer.parseInt(charFormData.getString("floors")),
                Integer.parseInt(charFormData.getString("surface")),
                Integer.parseInt(descFormData.getString("price")),
                Integer.parseInt(charFormData.getString("bedrooms")),
                Integer.parseInt(charFormData.getString("bathrooms")),
                Integer.parseInt(charFormData.getString("kitchens")),
                charFormData.getBoolean("hall"),
                charFormData.getBoolean("disableBathroom"),
                Integer.parseInt(charFormData.getString("internalCarPlaces")),
                Integer.parseInt(charFormData.getString("externalCarPlaces")),
                parseExtension(charFormData.getBoolean("pool"), charFormData.getString("poolSurface")),
                parseExtension(charFormData.getBoolean("garden"), charFormData.getString("gardenSurface")),
                true,
                charFormData.getString("furnishedStatus").equals("true")
            );
            case 'D', 'W' -> new Warehouse(
                0,
                DBManager.getInstance().getUserDao().find(contFormData.getString("username")),
                sellMode + "," +
                    typeFormData.getString("address") + "," +
                    typeFormData.getString("civicNumber") + "," +
                    contFormData.getString("email") + "," +
                    contFormData.getString("phoneNumber"),
                descFormData.getString("description"),
                typeFormData.getString("town"),
                descFormData.getFloat("lat"),
                descFormData.getFloat("lng"),
                charFormData.getString("propertyState"),
                charFormData.getString("energyClass"),
                heatingSystem,
                descFormData.getString("imageLink"),
                Integer.parseInt(charFormData.getString("floor")),
                Integer.parseInt(charFormData.getString("surface")),
                Integer.parseInt(descFormData.getString("price")),
                charFormData.getBoolean("square"),
                charFormData.getBoolean("office"),
                charFormData.getBoolean("alarm"),
                charFormData.getBoolean("garage") ? 1:0,
                Integer.parseInt(charFormData.getString("bathrooms")),
                charFormData.getBoolean("disableBathroom")
            );
            default -> throw new IllegalStateException("Unexpected value: " + propertyType);
        };

        // Forse non necessario
        int id = propertyDao.save(p);
        if (id != 0) {
            System.err.printf("Property salvata con id = %d\n", id);
            p.setId(id);
        } else {
            System.err.println("Property insertion Failed");
        }

        if (isParsingImages) try {
            System.out.println("Inizio procedura caricamento immaggini");
            for (int i = 0; i < imagesArray.length(); i++) {
                System.out.println("Iterazione " + i);
                JSONObject imageObject = imagesArray.getJSONObject(i);
                System.out.println("Oggetto creato ");
                String imageUrl = imageObject.getString("url");

                // Check if the URL is a data URI (base64 encoded image)
                if (imageUrl.startsWith("data:image")) {
                    System.out.println("Data trovato ");
                    // Extract the base64 data
                    String base64Data = imageUrl.split(",")[1];

                    System.out.println("Decode the base64 data");
                    byte[] imageData = Base64.getDecoder().decode(base64Data);

                    // Save the image to a file (you can customize the file path and name)
                    String outputPath = "upload/" + i + ".png";
                    System.out.println("save to " + outputPath);
                    try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                        fos.write(imageData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println("Image saved successfully: " + outputPath);
                } else {
                    System.out.println("Image URL is not a data URI: " + imageUrl);
                    // Handle or log non-data URI images as needed
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        } catch (JSONException e) {
            System.err.println("JSONException in HomeServlet");
            resp.getWriter().write("Failed to parse JSON. Received JSON body: " + jsonBody + "\n");
            e.printStackTrace(resp.getWriter());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON format");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Set CORS headers to allow requests from localhost:4200
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200"); // Update with your frontend's origin
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

        // Indicate successful processing of the preflight request
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private int parseExtension(boolean doesExist, String extension) {
        if (doesExist && !extension.isEmpty())
            return Integer.parseInt(extension);
        return 0;
    }
}
