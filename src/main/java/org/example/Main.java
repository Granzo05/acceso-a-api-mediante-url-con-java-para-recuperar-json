package org.example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ConexionDB conexion = new ConexionDB();

        for (int i = 1; i < 300; i++) {

            // Obtenemos el json con los datos completos
            JSONObject jsonObject = getJsonObject(i);
            if (jsonObject != null) {
                try {
                    Pais pais = new Pais();
                    pais.setCodigoPais(i);
                    pais.setNombrePais(jsonObject.getString("name"));
                    pais.setCapitalPais(jsonObject.getString("capital"));
                    pais.setRegion(jsonObject.getString("region"));
                    pais.setPoblacion(jsonObject.getInt("population"));
                    // Consigue cada dato del "latlng": Ejemplo: [6.5 (Index 0: Longitud),-9.5 (Index 1: Latitud)]
                    pais.setLongitud(jsonObject.getJSONArray("latlng").getDouble(0));
                    pais.setLatitud(jsonObject.getJSONArray("latlng").getDouble(1));

                    conexion.cargarDatos(pais);
                } catch (JSONException e) {
                    // Si da un error de algún dato faltante o directamente es nulo se omite ese dato
                }
            }
        }
    }

    private static JSONObject getJsonObject(int index) throws IOException {
        JSONObject jsonObject = null;
        try {
            URL url = new URL("https://restcountries.com/v2/callingcode/" + index);

            URLConnection connection = url.openConnection();

            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONArray jsonArray = new JSONArray(content.toString());

            jsonObject = jsonArray.getJSONObject(0);
            return jsonObject;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
