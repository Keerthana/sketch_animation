package com.cs349.mXMLParser;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.cs349.primitives.*;

public class SAXParserImpl {
	static _Frame tmpFrame;
	static _Stroke tmpStroke;
	static _Point tmpPoint;
	static ArrayList<_Frame> animation;
	String XMLfileName;
	static String tmpValue, tmpID;

	static boolean debug = false;

	public SAXParserImpl() {
		animation = new ArrayList<_Frame>();
	}

	public ArrayList<_Frame> getData(String file) {
		File f = new File(file);

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					tmpID = attributes.getValue("id");
					if (qName.equalsIgnoreCase("Frame")) {
						tmpFrame = new _Frame();
						tmpFrame.setFrameID(tmpID);
					}

					if (qName.equalsIgnoreCase("Stroke")) {

						tmpStroke = new _Stroke();
						tmpStroke.setStrokeID(tmpID);
					}

					if (qName.equalsIgnoreCase("Point")) {
						tmpPoint = new _Point();
						tmpPoint.setPointID(tmpID);
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					if (qName.equalsIgnoreCase("Frame")) {
						animation.add(tmpFrame);
					}

					if (qName.equalsIgnoreCase("Stroke")) {
						tmpFrame.addStroke(tmpStroke);
					}

					if (qName.equalsIgnoreCase("Point")) {
						tmpStroke.addPoint(tmpPoint);
					}
					if (qName.equalsIgnoreCase("x")) {
						tmpPoint.setX(tmpValue);
					}
					if (qName.equalsIgnoreCase("y")) {
						tmpPoint.setY(tmpValue);
					}
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
					tmpValue = new String(ch, start, length);
				}

			};

			saxParser
					.parse(new InputSource(new FileInputStream(file)), handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return animation;

	}

	public void printData(List<_Frame> anim) {
		for (_Frame _frame : anim) {
			for (_Stroke _stroke : _frame.getFrame()) {
				for (_Point _point : _stroke.getStroke()) {
					System.out.println(_point.getPoint() + ":"
							+ _point.getPointID() + "," + _stroke.getStrokeID()
							+ "," + _frame.getFrameID());
				}
			}
		}
	}
}