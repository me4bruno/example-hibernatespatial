package de.bruns.example.hibernatespatial;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.type.CustomType;
import org.hibernate.type.Type;
import org.hibernatespatial.GeometryUserType;
import org.hibernatespatial.criterion.SpatialRestrictions;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class MyAkwService {

   public List<MyAkwData> findAkwsWithinCriteria(Geometry filterGeometry) {
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();

      Criteria criteria = session.createCriteria(MyAkwData.class);
      criteria.add(SpatialRestrictions.within("location", filterGeometry));
      criteria.addOrder(Order.asc("description"));

      @SuppressWarnings("unchecked")
      List<MyAkwData> results = criteria.list();
      session.getTransaction().commit();
      return results;
   }

   public List<MyAkwData> findAkwsWithinHql(Geometry filterGeometry) {
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      
      Query hqlQuery = session.createQuery("from MyAkwData as ma where within(ma.location, :filterGeometry) = true order by ma.description");
      Type geomType = new CustomType(new GeometryUserType());
      hqlQuery.setParameter("filterGeometry", filterGeometry, geomType);
      
      @SuppressWarnings("unchecked")
      List<MyAkwData> results = hqlQuery.list();
      session.getTransaction().commit();
      return results;
   }
   
   private static final String RECTANGLE_GERMANY_NORTH = "POLYGON((5.5 51, 15.5 51, 15.5 55, 5.5 55, 5.5 51))";

   public static void main(String[] args) throws ParseException {
      MyAkwService myAkwService = new MyAkwService();
      GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
      WKTReader wktReader = new WKTReader(geometryFactory);
      List<MyAkwData> akws = myAkwService.findAkwsWithinCriteria(wktReader.read(RECTANGLE_GERMANY_NORTH));
      for (MyAkwData akw : akws) {
         System.out.println(akw.getDescription());
      }
   }
}
