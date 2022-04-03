package helper;

import com.google.gson.Gson;
import model.common.PostalAddress;
import model.common.SaleObjects;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ConvertHelper {
    /**This method returns SaleObject type from the .json file that has been read**/
    public static SaleObjects getSaleObjectsFromJSONFile() {
        Gson gson = new Gson();
        return gson.fromJson(readFromJsonFile(), SaleObjects.class);
    }

    /**This method reads context from .json file**/
    public static BufferedReader readFromJsonFile()
    {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/main/java/SaleObjectFiles/SaleObjects.json"));
        }
        catch (Exception exception)
        {
            System.out.println("File during reading from JSON File");
        }
        return bufferedReader;
    }

    /**This method reads context from .xml file and returns SaleObject type**/
    public static model.xml.SaleObjects getSaleObjectsFromXmlFile()
    {
        model.xml.SaleObjects saleObjects = null;
        try {
            File file = new File("src/main/java/SaleObjectFiles/SaleObjects.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(model.xml.SaleObjects.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            saleObjects = (model.xml.SaleObjects) jaxbUnmarshaller.unmarshal(file);
        }
        catch (Exception exception)
        {
            System.out.println("Fail during reading from XML File");
        }
        return saleObjects;
    }

    /**This method gether all SaleObjects from which come from different files**/
    public static void convertObjectsWhichComeFromXmlFileToCommonStructure(model.common.SaleObjects allObjects, model.xml.SaleObjects xmlFileSaleObjects, model.common.SaleObjects jsonFileSaleObjects)
    {
        allObjects.setNumberOfSaleObjects(jsonFileSaleObjects.getNumberOfSaleObjects() + xmlFileSaleObjects.getCount());
        allObjects.setSaleObjects(jsonFileSaleObjects.getSaleObjects());
        for(model.xml.SaleObject so : xmlFileSaleObjects.getSaleObjects())
        {
            model.common.SaleObject saleObject = model.common.SaleObject.builder()
                    .id(so.getId()).sizeSqm(so.getSizeSqm()).startingPrice(so.getStartingPriceAsBigInteger()).type(so.getType())
                    .postalAddress(PostalAddress.builder().city(so.getAddress().getCity()).street(so.getAddress().getStreet()).floor(so.getAddress().getFloor()).build())
                    .build();
            allObjects.getSaleObjects().add(saleObject);
        }

    }

}
