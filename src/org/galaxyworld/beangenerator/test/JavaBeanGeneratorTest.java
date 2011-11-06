package org.galaxyworld.beangenerator.test;

import org.galaxyworld.beangenerator.core.JavaBeanGenerator;
import org.galaxyworld.beangenerator.data.FieldData;
import org.galaxyworld.beangenerator.data.RootData;

public class JavaBeanGeneratorTest {

	public static void main(String[] args) {
        RootData root = new RootData();
        root.setClassName("enquiry");
        root.setPackageName("org.galaxyworld.bean");
        root.setComment("Enquiry.");
        root.setAuthor("Cheng Liang");
        root.setVersion("1.0.0");
        root.addField(new FieldData("currency", "String"));
        root.addField(new FieldData("amount", "Double", "Amount"));
        JavaBeanGenerator g = new JavaBeanGenerator(root);
		g.generate();
	}
	
}
