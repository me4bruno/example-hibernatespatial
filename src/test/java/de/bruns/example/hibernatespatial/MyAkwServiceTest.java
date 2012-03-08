package de.bruns.example.hibernatespatial;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class MyAkwServiceTest {

   private static final String[] AKWS_ALL = new String[] { "Biblis A+B", "Brokdorf", "Brunsbüttel", "Emsland",
         "Grafenrheinfeld", "Grohnde", "Gundremmingen B+C", "Isar/Ohu 1+2", "Krümmel", "Neckarwestheim 1+2",
         "Philippsburg 1+2", "Unterweser" };
   private static final String[] AKWS_NORTH = new String[] { "Brokdorf", "Brunsbüttel", "Emsland", "Grohnde",
         "Krümmel", "Unterweser" };

   private static final String RECTANGLE_GERMANY_ALL = "POLYGON((5.5 47, 15.5 47, 15.5 55, 5.5 55, 5.5 47))";
   private static final String RECTANGLE_GERMANY_NORTH = "POLYGON((5.5 51, 15.5 51, 15.5 55, 5.5 55, 5.5 51))";

   private WKTReader wktReader;
   private MyAkwService myAkwService;

   @Before
   public void setup() {
      myAkwService = new MyAkwService();
      GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
      wktReader = new WKTReader(geometryFactory);
   }

   @Test
   public void testFindAkwsWithinCriteria() throws ParseException {
      List<MyAkwData> akwsAll = myAkwService.findAkwsWithinCriteria(wktReader.read(RECTANGLE_GERMANY_ALL));
      assertEquals(12, akwsAll.size());
      for (int i = 0; i < akwsAll.size(); i++) {
         assertEquals(AKWS_ALL[i], akwsAll.get(i).getDescription());
      }

      List<MyAkwData> akwsNorth = myAkwService.findAkwsWithinCriteria(wktReader.read(RECTANGLE_GERMANY_NORTH));
      assertEquals(6, akwsNorth.size());
      for (int i = 0; i < akwsNorth.size(); i++) {
         assertEquals(AKWS_NORTH[i], akwsNorth.get(i).getDescription());
      }
   }
   
   @Test
   public void testFindAkwsWithinHql() throws ParseException {
      List<MyAkwData> akwsAll = myAkwService.findAkwsWithinHql(wktReader.read(RECTANGLE_GERMANY_ALL));
      assertEquals(12, akwsAll.size());
      for (int i = 0; i < akwsAll.size(); i++) {
         assertEquals(AKWS_ALL[i], akwsAll.get(i).getDescription());
      }
      
      List<MyAkwData> akwsNorth = myAkwService.findAkwsWithinHql(wktReader.read(RECTANGLE_GERMANY_NORTH));
      assertEquals(6, akwsNorth.size());
      for (int i = 0; i < akwsNorth.size(); i++) {
         assertEquals(AKWS_NORTH[i], akwsNorth.get(i).getDescription());
      }
   }

}
