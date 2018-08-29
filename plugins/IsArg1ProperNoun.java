package classes;

import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;
import id.ac.itb.openie.relation.Relation;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.lang3.tuple.Pair;
import id.ac.itb.openie.dataprocessor.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import id.ac.itb.nlp.POSTagger;



public class IsArg1ProperNoun extends Plugin {

    public IsArg1ProperNoun(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class IsArg1ProperNounHandler implements IFeatureHandler {

    	public String getFeatureName(){
    		String name = "Is Arg1 Proper Noun";
    		return name.replaceAll(" ","_");
    	}

    	public String getDescription(){
    		String description="berisi 'yes' jika argumen 1 merupakan 'Proper Noun'";
    		return description;
    	}

    	public Pair<String,Object> getAttributeType(){
    		String type="nominal";
    		List<String> values=Arrays.asList("yes","no");
    		return Pair.of(type,values);
    	}

    	public Object calculate(Relation relation){
    		String sentence=relation.getOriginSentence().trim();
            String arg1=relation.getRelationTriple().getLeft().trim();
    		if(isProperNoun(arg1,sentence)){
                return "yes";
            }else {
                return "no";
            }    	
        }

        public static String getTag(String s,String sentence){
            POSTagger postagger= new POSTagger();
            ArrayList<String[]> tagsentence = postagger.tag(sentence);
            String[] splitsentence=sentence.split("\\s+");
            return tagsentence.get(stringLocatedAt(s,splitsentence))[1];
        }
        
        public static boolean isProperNoun(String s,String sentence){
            if(s.split("\\s+").length==1 && getTag(s,sentence).equalsIgnoreCase("NNP")){
                return true;
            }else {
                return false;
            }
        }

         public static int stringLocatedAt(String word, String[] sentence){
            for(int i=0;i<sentence.length;i++){
                if(sentence[i].equalsIgnoreCase(word)){
                    return i;
                }
            }
            return -1;
        }

    }
}