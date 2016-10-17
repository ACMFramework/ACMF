package framework.traceability;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import framework.general.PropertyReader;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.XRFFSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

/** Provides classification models and output classified instances based on supplied data
 * 
 */
public class Classification 
{
	/**
	 * 
	 */
	PropertyReader propReader = new PropertyReader();
	
	/**
	 * 
	 */
	private String testDataArffPath = null;

	/**
	 * 
	 */
	private String trainingDataArffPath = null;

	/**
	 * 
	 */
	private String classifiedJ48 = null;

	/**
	 * 
	 */
	private String classifiedSVM = null;

	/**
	 * 
	 */
	private String classifiedMLP = null;

	/**
	 * 
	 */
	private String classifiedNaiveBayes = null;

	/**
	 * 
	 */
	private String xrffPath = null;

	/**
	 * 
	 * @return
	 */
	public String getXrffPath() 
	{
		return xrffPath;
	}

	/**
	 * 
	 * @param xrffPath
	 */
	public void setXrffPath(String xrffPath) 
	{
		this.xrffPath = xrffPath;
	}

	/**
	 * 
	 * @return
	 */
	public String getClassifiedNaiveBayes() 
	{
		return classifiedNaiveBayes;
	}

	/**
	 * 
	 * @param classifiedNaiveBayes
	 */
	public void setClassifiedNaiveBayes(String classifiedNaiveBayes) 
	{
		this.classifiedNaiveBayes = classifiedNaiveBayes;
	}

	/**
	 * 
	 * @return
	 */
	public String getClassifiedMLP() 
	{
		return classifiedMLP;
	}

	/**
	 * 
	 * @param classifiedMLP
	 */
	public void setClassifiedMLP(String classifiedMLP) 
	{
		this.classifiedMLP = classifiedMLP;
	}

	/**
	 * 
	 * @return
	 */
	public String getClassifiedSVM() 
	{
		return classifiedSVM;
	}

	/**
	 * 
	 * @param classifiedSVN
	 */
	public void setClassifiedSVM(String classifiedSVN) 
	{
		this.classifiedSVM = classifiedSVN;
	}

	/**
	 * 
	 * @return
	 */
	public String getClassifiedJ48() 
	{
		return classifiedJ48;
	}

	/**
	 * 
	 * @param classifiedJ48
	 */
	public void setClassifiedJ48(String classifiedJ48) 
	{
		this.classifiedJ48 = classifiedJ48;
	}

	/**
	 * 
	 * @return
	 */
	public String getTrainingDataArffPath() 
	{
		return trainingDataArffPath;
	}

	/**
	 * 
	 * @param trainingDataArffPath
	 */
	public void setTrainingDataArffPath(String trainingDataArffPath) 
	{
		this.trainingDataArffPath = trainingDataArffPath;
	}

	/**
	 * 
	 * @return
	 */
	public String getTestDataArffPath() 
	{
		return testDataArffPath;
	}

	/**
	 * 
	 */
	public void setTestDataArffPath(String arffPath) 
	{
		this.testDataArffPath = arffPath;
	}

	/** Constructor in which the output path of csv to arff conversion is set
	 * TODO: add to resources and specify path
	 */
	public Classification() 
	{
		setTestDataArffPath("C:\\Users\\Ildi\\Desktop\\2804.arff");
		setXrffPath("C:\\Users\\Ildi\\Desktop\\testDataXrff.xrff");
		setTrainingDataArffPath(propReader.getProperties()[19]);
		setClassifiedJ48("C:\\Users\\Ildi\\Desktop\\classifiedJ48.arff");
		setClassifiedSVM("C:\\Users\\Ildi\\Desktop\\classifiedSVM.arff");
		setClassifiedMLP("C:\\Users\\Ildi\\Desktop\\classifiedMLP.arff");
		setClassifiedNaiveBayes("C:\\Users\\Ildi\\Desktop\\classifiedNaiveBayes.arff");
	}

	/** Convert CSV input file to arff output file
	 * 
	 * @param inputPath
	 * @param outputPath
	 * @throws Exception 
	 */
	public void createArffFromCsv(String inputPath) throws Exception 
	{
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(inputPath));
		Instances data = loader.getDataSet();

		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(getTestDataArffPath()));
		saver.writeBatch();

		// Load in data from newly created arff file and check if class type is nominal, if not, change it to nominal
		ArffLoader l = new ArffLoader();
		l.setSource(new File(getTestDataArffPath()));
		Instances arffData = l.getDataSet();
		arffData.setClassIndex(arffData.numAttributes() - 1);
		
		// Convert class attribute to nominal
		Attribute classAttribute = arffData.classAttribute();
		if(!classAttribute.isNominal()) 
		{
			NumericToNominal nominal = new NumericToNominal();
			String[] options = new String[2];
			options[0] = "-R";
			options[1] = "17";
			nominal.setOptions(options);
			nominal.setInputFormat(arffData);
			Instances filteredData = Filter.useFilter(arffData, nominal);
			
			// Save file
			ArffSaver s2 = new ArffSaver();
			s2.setInstances(filteredData);
			s2.setFile(new File(getTestDataArffPath()));
			s2.writeBatch();
			
			tweakArffFile(getTestDataArffPath());
			
		}
	}

	/** Tweak arff file to add nominal values to curly braces
	 * @throws IOException 
	 * 
	 */
	public void tweakArffFile(String arffPath) throws IOException 
	{
		Path p = Paths.get(arffPath);
		byte[] b = Files.readAllBytes(p);
		String fileAsString = new String(b, Charset.defaultCharset());
		
		fileAsString = fileAsString.replace("{}", "{0,1}");
		Files.write(p, fileAsString.getBytes());
	}
	
	/**
	 * 
	 * @param inputPath
	 * @throws IOException
	 */
	public void saveArffToXrff(String inputPath) throws IOException 
	{
		ArffLoader loader = new ArffLoader();
		loader.setSource(new File(inputPath));
		Instances data = loader.getDataSet();

		XRFFSaver saver = new XRFFSaver();
		saver.setInstances(data);
		saver.setFile(new File(getXrffPath()));
		saver.writeBatch();
	}

	/** Classify test data using training data and the J48 algorithm and output to designated arff file
	 * @throws Exception 
	 * 
	 */
	public void classifyJ48() throws Exception 
	{
		// Load in training data
		ArffLoader arf = new ArffLoader();
		arf.setSource(new File(getTrainingDataArffPath()));
		Instances trainingDataSet = arf.getDataSet();
		trainingDataSet.setClassIndex(trainingDataSet.numAttributes()-1);

		// Load test data
		BufferedReader b = new BufferedReader(new FileReader(getTestDataArffPath()));
		Instances testDataSet = new Instances(b);
		testDataSet.setClassIndex(trainingDataSet.numAttributes()-1);
		b.close();

		J48 t = new J48();
		t.buildClassifier(trainingDataSet); 
		Instances classified = new Instances(testDataSet);
		// Label instances in the test set
		for(int i = 0; i < testDataSet.numInstances(); i++) 
		{
			System.out.println(i + "th instance: " + testDataSet.instance(i));
			double classLabel = t.classifyInstance(testDataSet.instance(i));
			classified.instance(i).setClassValue(classLabel);
		}
		// Save to file
		BufferedWriter w = new BufferedWriter(new FileWriter(getClassifiedJ48()));
		w.write(classified.toString());
		w.close();
	}

	/**
	 * @throws Exception 
	 * 
	 */
	public void classifyNaiveBayes() throws Exception 
	{
		// Load in training data
		ArffLoader arf = new ArffLoader();
		arf.setSource(new File(getTrainingDataArffPath()));
		Instances trainingDataSet = arf.getDataSet();
		trainingDataSet.setClassIndex(trainingDataSet.numAttributes()-1);

		// Load test data
		BufferedReader b = new BufferedReader(new FileReader(getTestDataArffPath()));
		Instances testDataSet = new Instances(b);
		testDataSet.setClassIndex(trainingDataSet.numAttributes()-1);
		b.close();

		NaiveBayes bayes = new NaiveBayes();
		bayes.buildClassifier(trainingDataSet); 
		Instances classified = new Instances(testDataSet);
		// Label instances in the test set
		for(int i = 0; i < testDataSet.numInstances(); i++) 
		{
			System.out.println(i + "th instance: " + testDataSet.instance(i));
			double classLabel = bayes.classifyInstance(testDataSet.instance(i));
			classified.instance(i).setClassValue(classLabel);
		}
		// Save to file
		BufferedWriter w = new BufferedWriter(new FileWriter(getClassifiedNaiveBayes()));
		w.write(classified.toString());
		w.close();
	}

	/**
	 * @throws Exception 
	 * 
	 */
	public void classifySVM() throws Exception 
	{
		// Load in training data
		ArffLoader arf = new ArffLoader();
		arf.setSource(new File(getTrainingDataArffPath()));
		Instances trainingDataSet = arf.getDataSet();
		trainingDataSet.setClassIndex(trainingDataSet.numAttributes()-1);

		// Load test data
		BufferedReader b = new BufferedReader(new FileReader(getTestDataArffPath()));
		Instances testDataSet = new Instances(b);
		testDataSet.setClassIndex(trainingDataSet.numAttributes()-1);
		b.close();

		SMO s = new SMO();
		s.buildClassifier(trainingDataSet);
		Instances classified = new Instances(testDataSet);
		// Label instances in the test set
		for(int i = 0; i < testDataSet.numInstances(); i++) 
		{
			System.out.println(i + "th instance: " + testDataSet.instance(i));
			double classLabel = s.classifyInstance(testDataSet.instance(i));
			classified.instance(i).setClassValue(classLabel);
		}
		// Save to file
		BufferedWriter w = new BufferedWriter(new FileWriter(getClassifiedSVM()));
		w.write(classified.toString());
		w.close();
	}

	/**
	 * @throws Exception 
	 * 
	 */
	public void classifyMLP() throws Exception 
	{
		// Load in training data
		ArffLoader arf = new ArffLoader();
		arf.setSource(new File(getTrainingDataArffPath()));
		Instances trainingDataSet = arf.getDataSet();
		trainingDataSet.setClassIndex(trainingDataSet.numAttributes()-1);

		// Load test data
		BufferedReader b = new BufferedReader(new FileReader(getTestDataArffPath()));
		Instances testDataSet = new Instances(b);
		testDataSet.setClassIndex(trainingDataSet.numAttributes()-1);
		b.close();

		MultilayerPerceptron mlp = new MultilayerPerceptron();
		mlp.setLearningRate(0.1);
		mlp.setMomentum(0.2);
		mlp.setTrainingTime(2000);
		mlp.setHiddenLayers("3");
		mlp.buildClassifier(trainingDataSet);

		Instances classified = new Instances(testDataSet);
		// Label instances in the test set
		for(int i = 0; i < testDataSet.numInstances(); i++) 
		{
			System.out.println(i + "th instance: " + testDataSet.instance(i));
			double classLabel = mlp.classifyInstance(testDataSet.instance(i));
			classified.instance(i).setClassValue(classLabel);
		}
		// Save to file
		BufferedWriter w = new BufferedWriter(new FileWriter(getClassifiedSVM()));
		w.write(classified.toString());
		w.close();

	}

	/**
	 * 
	 */
	public void classifyZeroRBaseline() 
	{

	}

	/**
	 * 
	 */
	public void classifyOneR() 
	{

	}

	/**
	 * 
	 */
	public void crossValidate() 
	{
		
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception 
	{
		Classification c = new Classification();
		//c.createArffFromCsv("C:\\Users\\Ildi\\Desktop\\r.csv");
		//c.classifyNaiveBayes();
		c.saveArffToXrff("C:\\Users\\Ildi\\Desktop\\classifiedNaiveBayes.arff");
	}

}
