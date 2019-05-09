/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.itb.openie.classifier;

import java.io.File;
import java.util.HashMap;

import weka.core.Instances;

/**
 *
 * @author yoga
 */
public interface IClassifierPipelineElement {
    public HashMap<File, Instances> execute(File file, Instances dataset, Instances datatraining) throws Exception;
    public HashMap<File, Instances> read() throws Exception;
    public void write(File file, Instances instances) throws Exception;
}
