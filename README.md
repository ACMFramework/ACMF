# ACM Framework

The Artefact Consistency Management (ACM) Framework is a PhD research prototype / proof-of-concept tool written in Java. 
The aim of ACM is to support managing the consistency of heterogeneous software artefacts (the current implementation caters for 
Java source code, UML diagrams (class, sequence and use case), JUnit test and software architecture (module and conceptual view) 
artefacts), specifically:

* The extraction of fine-grained information from original artefacts
* The transformation of heterogeneous schemas to a property graph representation, where artefact elements are represented by nodes, 
and trace links connecting them are modelled by edges of the graph.
* Incorporating a machine learning approach to semi-automatically set up trace links between artefact elements. 
Artefact elements and their links are stored in a graph database (Neo4j).
* The detection and identification of changes to artefacts.
* Graph-based change impact analysis to identify other elements potentially impacted by the modification
* Rule-based consistency checking of potentially impacted artefact elements
* Providing suggestions on resolving (potential) inconsistencies.

##Usage

The current implementation does not provide a user interface. The user can invoke the framework's functionality in the *main method* of 
the *Startup class (framework.general package)*. This will firstly perform *Framework setup* (manageSetup() method) to extract
artefacts from the specified repository, to transform them to .graphml files through XSLT transformation and to import them to the 
specified graph database (currently Neo4j). The repository and database paths are configured in the *config.properties* file. 
The .graphml files are stored in the local file system in the framework directory (ACMF) and its subdirectories. Trace links can be either
manually specified based on the contents of the .graphml files or using the approach provided by the framework to automate trace link 
creation. The functionality can be invoked by calling the manageTraceLinkCreation() method in the Startup class and it is described in
[ACMTrceability tool] (https://github.com/ACMFramework/ACMTraceability).

**Pre-requisites:**

* Neo4j (version 2.1.3)
* Mercurial (and local and remote repository paths)
* Java (version 7)
* Original artefacts are in local Mercurial repository
* Artefacts can be exported to an XML-based representation (The src2ml tool (http://www.srcml.org/) can be used to extract Java, C, C# and C++ source code files. Other artefacts may require manual export.)
* The "ACM" folder and the following subfolders ("ArchitectureConceptual", "ArchitectureModuleView", "Requirement", "SourceCode", "UMLClass", "UMLSequence", "UMLUseCase", and "UnitTests") are setup in the local file system. When using src2ml for artefact data extraction, the required executables should be copied in the "SourceCode" and "UnitTests" subfolders.
* Additional XSLT files for new artefact types are supplied and are configured in the *config.properties* file

Following framework setup and establishing trace links, the framework is ready to perform consistency management. This is invoked following
a change has been made in the Mercurial repository by calling the manageConsistency() method in the Startup class. 

##Output

At the end of the consistency management process, the framework provides suggestions on resolving (potential) inconsistencies.

In case no inter impact analysis and inter consistency checking took place, the following message is displayed:
*"Check connections are in place. No inter impact analysis and inter consistency checking took place."*

The following example shows the results in case a Java source code field has been deleted and that its container class should be edited
in case removing the field affects other entities within the class.

*"The log Field has been edited. Change type = DELETE 
Change propagation suggestion: 
Edit Node id:sc0C:\Users\I\ACMF\SourceCode\ChatBot.graphml, Node type:class."*

##Similar Projects

EMFTrace (https://sourceforge.net/projects/emftrace/)
CLIME (http://cs.brown.edu/~spr/research/envclime.html)
ArchTrace (https://github.com/gems-uff/archtrace)
