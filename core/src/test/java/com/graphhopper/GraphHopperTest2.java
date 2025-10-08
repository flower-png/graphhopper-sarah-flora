package com.graphhopper;

import com.github.javafaker.Faker;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.LMProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.reader.ReaderWay;
import com.graphhopper.reader.dem.SRTMProvider;
import com.graphhopper.reader.dem.SkadiProvider;
import com.graphhopper.routing.TestProfiles;
import com.graphhopper.routing.ev.*;
import com.graphhopper.routing.util.AllEdgesIterator;
import com.graphhopper.routing.util.DefaultSnapFilter;
import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.routing.util.countryrules.CountryRuleFactory;
import com.graphhopper.routing.util.parsers.OSMRoadEnvironmentParser;
import com.graphhopper.routing.weighting.Weighting;
import com.graphhopper.storage.IntsRef;
import com.graphhopper.storage.index.LocationIndexTree;
import com.graphhopper.storage.index.Snap;
import com.graphhopper.util.*;
import com.graphhopper.util.Parameters.CH;
import com.graphhopper.util.Parameters.Landmark;
import com.graphhopper.util.Parameters.Routing;
import com.graphhopper.util.details.PathDetail;
import com.graphhopper.util.exceptions.ConnectionNotFoundException;
import com.graphhopper.util.exceptions.MaximumNodesExceededException;
import com.graphhopper.util.exceptions.PointDistanceExceededException;
import com.graphhopper.util.shapes.BBox;
import com.graphhopper.util.shapes.GHPoint;
import com.graphhopper.util.shapes.GHPoint3D;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.graphhopper.json.Statement.If;
import static com.graphhopper.json.Statement.Op.LIMIT;
import static com.graphhopper.json.Statement.Op.MULTIPLY;
import static com.graphhopper.util.GHUtility.createCircle;
import static com.graphhopper.util.GHUtility.createRectangle;
import static com.graphhopper.util.Parameters.Algorithms.*;
import static com.graphhopper.util.Parameters.Curbsides.*;
import static com.graphhopper.util.Parameters.Details.STREET_REF;
import static com.graphhopper.util.Parameters.Routing.TIMEOUT_MS;
import static com.graphhopper.util.Parameters.Routing.U_TURN_COSTS;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * GraphHopperTest2 est une classe de 7 tests juste pour le devoir
 * Pour éviter l'éxécution de 3000 LIGNES DE CODE!!!
 */

public class GraphHopperTest2 {
    public static final String DIR = "../core/files";

        // map locations
        private static final String BAYREUTH = DIR + "/north-bayreuth.osm.gz";
        private static final String BAUTZEN = DIR + "/bautzen.osm";
        private static final String BERLIN = DIR + "/berlin-siegessaeule.osm.gz";
        private static final String KREMS = DIR + "/krems.osm.gz";
        private static final String LAUF = DIR + "/Laufamholzstrasse.osm.xml";
        private static final String MONACO = DIR + "/monaco.osm.gz";
        private static final String MOSCOW = DIR + "/moscow.osm.gz";
        private static final String ESSEN = DIR + "/edge_based_subnetwork.osm.xml.gz";

    // when creating GH instances make sure to use this as the GH location such that it will be cleaned between tests
        private static final String GH_LOCATION = "target/graphhopper-test-gh";

        @BeforeEach
        @AfterEach
        public void setup() {
                Helper.removeDir(new File(GH_LOCATION));
        }

        @ParameterizedTest
        @CsvSource({
                        DIJKSTRA + ",false,703",
                        ASTAR + ",false,361",
                        DIJKSTRA_BI + ",false,340",
                        ASTAR_BI + ",false,192",
                        DIJKSTRA_BI + ",true,45",
                        ASTAR_BI + ",true,43",
        })

        @Test
        void getCountryRuleFactory() {

                GraphHopper hopper = new GraphHopper();
                CountryRuleFactory countryRuleFactory = new CountryRuleFactory();
                
                // set et get countryRuleFactory
                hopper.setCountryRuleFactory(countryRuleFactory);
                assertEquals(countryRuleFactory, hopper.getCountryRuleFactory());

                // set countryRuleFactory à null
                hopper.setCountryRuleFactory(null);
                assertNull(hopper.getCountryRuleFactory());
        }

        @Test
        void getImportRegistry() {

                GraphHopper hopper = new GraphHopper();
                ImportRegistry importRegistry = new DefaultImportRegistry();
                
                // set et get importRegistry
                hopper.setImportRegistry(importRegistry);
                assertEquals(importRegistry, hopper.getImportRegistry());
        }

        @Test
        void get_ET_setCustomAreasDirectory() {

                GraphHopper hopper = new GraphHopper();
                String fakerPathString = new Faker().file().fileName(); // définir chemin aléatoire

                // set et get avec chemin aléatoire
                hopper.setCustomAreasDirectory(fakerPathString);
                assertEquals(fakerPathString, hopper.getCustomAreasDirectory());
        }

        @Test
        void setLocationIndexThrowsIllegalStateException() {
                // locationIndex non initialisé devrait lancer exception
                assertThrows(IllegalStateException.class, () -> new GraphHopper().getLocationIndex(),
                                "LocationIndex not initialized");
        }

        @Test
        void getBaseGraphThrowsIllegalStateException() {
                // baseGraph non initialisé devrait lancer exception
                IllegalStateException exception = assertThrows(IllegalStateException.class,
                                () -> new GraphHopper().getBaseGraph());
                assertEquals("GraphHopper storage not initialized", exception.getMessage());
        }

        @Test
        void setOSMFileThrowsIllegalArgumentException() {
                // paramètre string vide devrait lancer exception
                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                                () -> new GraphHopper().setOSMFile(""));
                assertEquals("OSM file cannot be empty.", exception.getMessage());
        }

        @Test
        void setGraphHopperLocationThrowsIllegalArgumentException() {
                // paramètre ghLocation null devrait lancer exception
                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                                () -> new GraphHopper().setGraphHopperLocation(null));
                assertEquals("graphhopper location cannot be null", exception.getMessage());
        }
}
