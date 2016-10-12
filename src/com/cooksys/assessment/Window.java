package com.cooksys.assessment;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.BoxLayout;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;

import javax.swing.JDesktopPane;
import javax.swing.JMenu;

import java.awt.SystemColor;
import java.awt.Insets;
import java.io.File;

import javax.swing.AbstractListModel;
import javax.swing.UIManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class Window {

	private JFrame frmPcPartBuilder;

	/**
	 * Launch the application. The main method is the entry point to a Java application. 
	 * For this assessment, you shouldn't have to add anything to this.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frmPcPartBuilder.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application. This is the constructor for this Window class.
	 * All of the code here will be executed as soon as a Window object is made.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. This is where Window Builder
	 * will generate its code.
	 */
	public void initialize() {
		// create the main window
		frmPcPartBuilder = new JFrame();
		frmPcPartBuilder.getContentPane().setBackground(SystemColor.scrollbar);
		frmPcPartBuilder.setTitle("PC Part Builder");
		frmPcPartBuilder.setBounds(100, 100, 381, 503);
		frmPcPartBuilder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPcPartBuilder.getContentPane().setLayout(new BorderLayout(0, 0));
		
		// create desktopPane for arranging the other components
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(SystemColor.scrollbar);
		frmPcPartBuilder.getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		// Create the JList for the right side of the window - the side that will be added to and removed from
		// The DefaultMOdelList is used to make it easier to add and remove from the JList
		final DefaultListModel<String> rightListmodel = new DefaultListModel();
		final ListItems rightItems = new ListItems();
		final JList rightList = new JList(rightListmodel);
		rightList.setBounds(257, 0, 108, 464);
		desktopPane.add(rightList);
		
		// Create the JList for the left side of the window - the static side
		final DefaultListModel<String> partsName = new DefaultListModel<String>();
		// Add the static list to the left side
		partsName.addElement("Case");
		partsName.addElement("Motherboard");
		partsName.addElement("CPU");
		partsName.addElement("GPU");
		partsName.addElement("PSU");
		partsName.addElement("RAM");
		partsName.addElement("HDD");
		final JList leftList = new JList(partsName);
		leftList.setBounds(0, 0, 108, 464);
		desktopPane.add(leftList);		
		
		// This is the button that will add to the right list
		JButton addButton = new JButton(">>");
		addButton.addActionListener(new ActionListener() {
			// add data from left list to right list by getting the selected index and converting to a string
			public void actionPerformed(ActionEvent arg0) {
				rightListmodel.addElement(leftList.getSelectedValue().toString());
				rightItems.add(leftList.getSelectedValue().toString());
			} // end actionPerformed()
		}); // end addButton.addActionListener
		addButton.setBackground(UIManager.getColor("Button.background"));
		addButton.setBounds(139, 174, 89, 23);
		desktopPane.add(addButton);
		
		// This is the button that will remove from the right side
		JButton removeButton = new JButton("<<");
		removeButton.addActionListener(new ActionListener() {
			// remove item from right side
			public void actionPerformed(ActionEvent arg0) {
				// get the selected item from the right side
				int index = rightList.getSelectedIndex();
				// remove that item from the DefaultListModel for the right side
				rightListmodel.remove(index);
				rightItems.remove(index);
			} // end actionPerformed()
		}); // end removeButton.addActionListener()
		removeButton.setBounds(139, 208, 89, 23);
		desktopPane.add(removeButton);
		
		// Create the menu bar at the top of the screen
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setForeground(SystemColor.scrollbar);
		menuBar.setBackground(SystemColor.scrollbar);
		frmPcPartBuilder.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		// Create the menu item File
		JMenu fileMenu = new JMenu("File");
		fileMenu.setBackground(Color.LIGHT_GRAY);
		menuBar.add(fileMenu);
		
		// Create the menu item Load
		JMenuItem loadMenu = new JMenuItem("Load");
		loadMenu.addActionListener(new ActionListener() {
			// simply calls the load method
			public void actionPerformed(ActionEvent arg0) {
				load();
			} // end actionPerformed			
			private void load() {
				// clear the right side of stuff, one by one
				int elements = rightListmodel.getSize();
				for(int i = 0; i < elements; i++) {
					rightListmodel.remove(0);
				} // end for
				rightItems.clear();
				
				// unmarshall the data inside the XML file and put it into a ListItems object
				try {
					File file = new File("data.xml");
					JAXBContext jaxbContext = JAXBContext.newInstance(ListItems.class);

					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					ListItems rightItems2 = (ListItems) jaxbUnmarshaller.unmarshal(file);
					rightItems.copyList(rightItems2.returnList());
					
				  } catch (JAXBException e) {
					e.printStackTrace();
				  } // end try/catch
				
				// get all the elements that were just unmarshalled and put them into the right side JList
				int size = rightItems.getSize();
				for(int i = 0; i < size; i++) {
					rightListmodel.addElement(rightItems.getElement(i));
				} // end for				  
			} // end load()
		}); // end loadMenu.addActionListener()
		fileMenu.add(loadMenu);
		
		// Create the menu item Save
		JMenuItem saveMenu = new JMenuItem("Save");
		saveMenu.addActionListener(new ActionListener() {
			// simply calls the save method
			public void actionPerformed(ActionEvent e) {
				save();
			} // end actionPerformed()
			private void save() {
				// convert the data inside the ListItems into XML
				try {
					File file = new File("data.xml");
					JAXBContext jaxbContext = JAXBContext.newInstance(ListItems.class);
					Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
					jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					jaxbMarshaller.marshal(rightItems, file);
				} // end try 
				catch (JAXBException e) {
					e.printStackTrace();
				} // end catch
			} // end save()
		}); // end saveMenu.addActionListener()
		fileMenu.add(saveMenu);
		
		// Create the menu item Exit
		JMenuItem exitMenu = new JMenuItem("Exit");
		exitMenu.addActionListener(new ActionListener() {
			// this will end the program upon being pressed
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			} // end actionPerformed()
		}); // exitMenu.addActionListener()
		fileMenu.add(exitMenu);
	} // end initialize()
} // end Class Window
