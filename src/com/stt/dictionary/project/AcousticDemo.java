package com.stt.dictionary.project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.util.props.PropertyException;

public class AcousticDemo {

	static String resultText;
	static String answerToStr;
	static String textToSpeech;
	static Process pro;
	static Random ran = new Random();


	/**
	 * Launch the application.
	 * @throws PropertyException 
	 * @throws IOException 
	 * @throws InstantiationException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, PropertyException, InstantiationException, InterruptedException {
		/**
		 * GUI Component
		 */
			JFrame SpeechFrame = new JFrame("Speech To Text");
			SpeechFrame.getContentPane().setBackground(new Color(255, 255, 255));
			SpeechFrame.getContentPane().setForeground(new Color(0, 0, 0));
			SpeechFrame.setSize(800, 600);
			SpeechFrame.setLocationRelativeTo(null);
			SpeechFrame.setResizable(false);
			SpeechFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			JLabel mainBgImage = new JLabel();
			SpeechFrame.getContentPane().add(mainBgImage);
			mainBgImage.setIcon(null);
			SpeechFrame.setVisible(true);
			mainBgImage.setOpaque(false);
			SpeechFrame.getContentPane().setLayout(null);
			
			/*
			 * Output Window
			 */


			/*
			 * Speech Recognition Components
			 */
				
			// Loading configuration file
				
			Configuration configuration = new Configuration();

	        configuration.setAcousticModelPath("resources/en-us/en-us");
//	        configuration.setDictionaryPath("resources/en-us/cmudict-en-us.dict");
	        configuration.setDictionaryPath("resources/en-us/dicts.dict");
	        configuration.setLanguageModelPath("resources/en-us/en-us.lm.bin");
				

			
			
						//Listening Notification

			JLabel listening = new JLabel();
			SpeechFrame.getContentPane().add(listening);
			listening.setForeground(new Color(0, 0, 0));
			listening.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
			listening.setBounds(152, 0, 196, 30);
			listening.setText("  Loading ...");
//			listening.setText("Listening...");
			
			
			JTextPane textPane = new JTextPane();
			SpeechFrame.getContentPane().add(textPane);
			textPane.setBounds(0, 41, 800, 450);
			textPane.setBackground(new Color(228, 228, 228));
			textPane.setForeground(new Color(2, 82, 167));
			textPane.setFont(new Font("Monotype Corsiva", Font.PLAIN, 24));
			textPane.setEditable(false);
			
			JButton save_btn = new JButton("Save File");
			SpeechFrame.getContentPane().add(save_btn);
			save_btn.setBounds(0, 491, 800, 90);
			save_btn.setFont(new Font("Times New Roman", Font.BOLD, 20));
			save_btn.setBorderPainted(true);
			save_btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					JFileChooser savefile = new JFileChooser();
			        savefile.setSelectedFile(new File("C:/Users/Suz Zan/Desktop/SaveFile.txt"));
//			        savefile.showSaveDialog(savefile);
			        BufferedWriter writer;
			        
			        int sf = savefile.showSaveDialog(null);
			        if(sf == JFileChooser.APPROVE_OPTION){
			            try {
			            	
			            	writer = new BufferedWriter(new FileWriter(savefile.getSelectedFile()));
			              
			                writer.append(textPane.getText());
			                writer.close();
			                JOptionPane.showMessageDialog(null, "File has been saved","File Saved",JOptionPane.INFORMATION_MESSAGE);
			                // true for rewrite, false for override

			            } catch (IOException e) {
			                e.printStackTrace();
			            }
			        }else if(sf == JFileChooser.CANCEL_OPTION){
			            JOptionPane.showMessageDialog(null, "File save has been canceled");
			        }
			        
				}
			});
				
			// Loop the recognition until the program exits
				
				while(true){
					listening.setVisible(true);
//					listening.setText("Listening...");
					try{
						LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
						listening.setText("Listening...");
			            recognizer.startRecognition(true);

			            //create SpeechResult Obj
			            SpeechResult result = recognizer.getResult();
					
						if(result != null){ 
							
							while ((result = recognizer.getResult()) != null) {
//				                System.out.format("Hypothesis: %s\n", result.getHypothesis());
				                textPane.setText(textPane.getText()+ result.getHypothesis() + " ");
				            }
							
							
//								
//							if(result.getHypothesis() == "exit"){
//					            recognizer.stopRecognition();
//								textPane.setText(resultText + "\n \n" + " Clearing texts and Exiting......  ");
//								TimeUnit.SECONDS.sleep(4);
//								System.exit(1);
//								
//							}									
						}				
		
					}catch(IOException e){}
				}	}		
	}
	
