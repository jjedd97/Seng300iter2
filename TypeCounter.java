import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;


public class TypeCounter
{
	
	public static void main(String[] args) throws FileNotFoundException, IllegalStateException, IOException
	{
		//intialize counters 
		DeclarationCounter dcounter=new DeclarationCounter();
		ReferenceCounter rcounter=new ReferenceCounter();
		//store command line argument
		Path inputPath = Paths.get(args[0]);
		Path fullPath = inputPath.toAbsolutePath();
		File directory = new File(args[0]);
	     //check files in directory
		if (!(directory.isDirectory())) {
			throw new IllegalStateException("Path specified is not a directory");
		}
		//get all the files from a directory
		   File[] fileList = directory.listFiles();
		   //instantiate file checker
		   //find java files and parse
		   FileHandler fhandle=new FileHandler();
		   String path=args[0];
   		   String [] patharray= {path};
		   for (File file : fileList){
		       if (file.isFile()){
		    	boolean isjavafile;
		        isjavafile=fhandle.CheckFile(file);
		        if (isjavafile)
		        {
		        	ASTParser parser = ASTParser.newParser(AST.JLS8);
		    	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
		    	    parser.setResolveBindings(true);
		    	    parser.setBindingsRecovery(true);
		    		Map options = JavaCore.getOptions();
		    		parser.setCompilerOptions(options);
		    		parser.setEnvironment(patharray, patharray, null, true);
		        	parser.setUnitName(file.getName());
		        	String strfile;
		        	//turn file to a string
		        	strfile=fhandle.getFileContent(file);
		        	//parse
		        	parser.setSource(strfile.toCharArray()); 
		    	    CompilationUnit cu = (CompilationUnit)parser.createAST(null);
		    	    System.out.println();
		    	    //count declarations
		    	    dcounter.updateCounter(cu);
		    	    //count references
		    	    rcounter.updateCounter(cu);
		    		}
		        }
		       } 
		   //outside for loop
		   List <String> rlist=rcounter.getList();
		   List <String> dlist=dcounter.getList();
		   
		   while((!rlist.isEmpty())||(!dlist.isEmpty()))
		   {
			   int rcount=0;
			   int dcount=0;
			   String type="";
			   
			   if (!(rlist.isEmpty()))
			   {
				  type=rlist.get(0);
				  while (rlist.remove(type))
				  {
				  rcount++;
				  }
				  while (dlist.remove(type))
				  {
				  dcount++;
				  }
			   }
				  else if (!(dlist.isEmpty())&&rlist.isEmpty())
				   {
					  type=dlist.get(0);
					  while (dlist.remove(type))
					  {
					  dcount++;
					  }
					  while (rlist.remove(type))
					  {
					  rcount++;
					  }
				   }
					  else if (!(rlist.isEmpty())&&dlist.isEmpty())
					   {
						  type=rlist.get(0);
						  while (dlist.remove(type))
						  {
						  dcount++;
						  }
						  while (rlist.remove(type))
						  {
						  rcount++;
						  }
				  
			   }
				  System.out.println(type + ": Declarations found: " + dcount + "; References found: " + rcount + ".");
		   }
	 
		   }  
		  
	
		
	
}
