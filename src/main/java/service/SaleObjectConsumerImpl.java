package service;

import exception.TechnicalException;
import helper.ConvertHelper;
import model.common.SaleObjects;
import model.enums.PriorityOrderAttribute;
import model.xml.SaleObject;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.Scanner;

public class SaleObjectConsumerImpl implements SaleObjectConsumer{
    private SaleObjects saleObjectsFromJsonFile;
    private model.xml.SaleObjects saleObjectsFromXmlFile;
    private final SaleObjects allObjects = new SaleObjects();

    @Override
    public PriorityOrderAttribute getPriorityOrderAttribute() {
        System.out.println("Please type the number of the priority that you want to order: \n 1 for City, 2 for SquareMeters, 3 for PricePerSquareMeter");
        Scanner scanner = new Scanner(System.in);
        try
        {
            String enteredPriority = scanner.nextLine();
            if(enteredPriority.equals("2"))
            {
                System.out.println("LISTING ALL SALE OBJECTS IN ORDER 'SQUARE_METERS' THEN CITY\n");
                return PriorityOrderAttribute.SquareMeters;
            }
            else if(enteredPriority.equals("3"))
            {
                System.out.println("LISTING ALL SALE OBJECTS IN ORDER 'PRICE_PER_SQUARE_METER' THEN CITY\n");
                return PriorityOrderAttribute.PricePerSquareMeter;
            }
            else
            {
                System.out.println("LISTING ALL SALE OBJECTS IN ORDER 'CITY' THEN 'PRICE_PER_SQUARE_METER'\n");
                return PriorityOrderAttribute.City;
            }
        }
        catch (Exception exception)
        {
            System.out.println("LISTING ALL SALE OBJECTS IN ORDER 'CITY' THEN 'PRICE_PER_SQUARE_METER'\n");
            return PriorityOrderAttribute.City;
        }
    }

    @Override
    public void startSaleObjectTransaction() {
        this.saleObjectsFromJsonFile = ConvertHelper.getSaleObjectsFromJSONFile();
        this.saleObjectsFromXmlFile = ConvertHelper.getSaleObjectsFromXmlFile();
    }

    @Override
    public void reportSaleObject(int squareMeters, String pricePerSquareMeter, String city, String street, Integer floor) throws TechnicalException{

        if(city.equals("") || street.equals("") || squareMeters<0 || pricePerSquareMeter.equals(""))
        {
            throw new TechnicalException("Check the values on SaleObject!!!");
        }

        System.out.println("City: " + city + "\nStreet: " + street + "\nFloor: " + floor + "\nSquareMeter: " + squareMeters + "\nPricePerSquareMeter: " + pricePerSquareMeter);
        System.out.println("---------------------");
    }

    @Override
    public void commitSaleObjectTransaction() {

    }

    /**
     * This method order the sale objects according to priority that user has selected. **/
    void arrangeSaleObjectsAccordingToPriorities(PriorityOrderAttribute priorityOrderAttribute)
    {
        getherAllObjects();
        switch (priorityOrderAttribute)
        {
            case City:
                saleObjectsFromJsonFile.getSaleObjects()
                        .sort(Comparator.comparing((model.common.SaleObject saleObject) -> saleObject.getPostalAddress().getCity())
                                .thenComparing((model.common.SaleObject saleObject) -> saleObject.getStartingPrice().divide(BigInteger.valueOf(saleObject.getSizeSqm()))));
                break;
            case PricePerSquareMeter:
                saleObjectsFromJsonFile.getSaleObjects()
                        .sort(Comparator.comparing((model.common.SaleObject saleObject) -> saleObject.getStartingPrice().divide(BigInteger.valueOf(saleObject.getSizeSqm())))
                                .thenComparing((model.common.SaleObject saleObject) -> saleObject.getPostalAddress().getCity()));

                break;
            case SquareMeters:
                saleObjectsFromJsonFile.getSaleObjects()
                        .sort(Comparator.comparing(model.common.SaleObject::getSizeSqm)
                                .thenComparing(((model.common.SaleObject saleObject) -> saleObject.getPostalAddress().getCity())));
                break;
            default:
                break;
        }
    }

    /**
     * This method converts the starting prices that had been given with dot format to BigInteger type**/
    void convertStringStartingPricesToBigInteger()
    {
        for(SaleObject saleObject : saleObjectsFromXmlFile.getSaleObjects())
        {
            String startingPriceWithoutDots = saleObject.getStartingPrice().replace(".","");
            BigInteger startingPriceAsBigInteger = BigInteger.valueOf(Long.parseLong(startingPriceWithoutDots));
            saleObject.setStartingPriceAsBigInteger(startingPriceAsBigInteger);
        }
    }

    void getherAllObjects()
    {
        convertStringStartingPricesToBigInteger();
        ConvertHelper.convertObjectsWhichComeFromXmlFileToCommonStructure(allObjects, saleObjectsFromXmlFile, saleObjectsFromJsonFile);
    }

    /**
     * Reports all saleObjects from any type of file**/
    void reportAllSaleObjects()
    {
        System.out.println("ORDERING SALE OBJECTS FROM FILES");
        for(model.common.SaleObject saleObject : allObjects.getSaleObjects())
        {
            BigInteger pricePerSquareMeter = saleObject.getStartingPrice().divide(BigInteger.valueOf(saleObject.getSizeSqm()));
            reportSaleObject(saleObject.getSizeSqm(), pricePerSquareMeter.toString(), saleObject.getPostalAddress().getCity(),
                    saleObject.getPostalAddress().getStreet(), saleObject.getPostalAddress().getFloor());
        }
    }

    public static void main(String[] args) {
        SaleObjectConsumerImpl saleObjectConsumer = new SaleObjectConsumerImpl();
        PriorityOrderAttribute selectedPriorityOrderAttribute = saleObjectConsumer.getPriorityOrderAttribute();
        saleObjectConsumer.startSaleObjectTransaction();
        saleObjectConsumer.arrangeSaleObjectsAccordingToPriorities(selectedPriorityOrderAttribute);
        saleObjectConsumer.reportAllSaleObjects();
        saleObjectConsumer.commitSaleObjectTransaction();
    }


}
