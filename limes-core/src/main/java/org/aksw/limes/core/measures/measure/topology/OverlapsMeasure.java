package org.aksw.limes.core.measures.measure.topology;

import org.aksw.limes.core.io.cache.Instance;
import org.aksw.limes.core.measures.measure.AMeasure;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * Measure that checks for the topological relation overlaps.
 *
 * @author kdressler
 */
public class OverlapsMeasure extends AMeasure {
    @Override
    public double getSimilarity(Object object1, Object object2) {
        // expects WKT Strings
        String sWKT, tWKT;
        Geometry sGeo, tGeo;
        sWKT = object1.toString();
        tWKT = object2.toString();
        WKTReader reader = new WKTReader();
        try {
            sGeo = reader.read(sWKT);
            tGeo = reader.read(tWKT);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0d;
        }
        return sGeo.overlaps(tGeo) ? 1d : 0d;
    }

    @Override
    public double getSimilarity(Instance instance1, Instance instance2, String property1, String property2) {
        double value = 0;
        double sim = 0;
        for (String source : instance1.getProperty(property1)) {
            for (String target : instance2.getProperty(property2)) {
                sim = getSimilarity(source, target);
                if (sim > value) {
                    value = sim;
                }
            }
        }
        return sim;
    }

    @Override
    public double getRuntimeApproximation(double mappingSize) {
        return mappingSize / 1000d;
    }

    @Override
    public String getName() {
        return "top_overlaps";
    }

    @Override
    public String getType() {
        return "topology";
    }
}
