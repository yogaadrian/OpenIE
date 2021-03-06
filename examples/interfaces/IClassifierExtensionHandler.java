/**
 * This is a simplified schema that shows each methods a user has to implement
 */
public abstract class IClassifierExtensionHandler implements IClassifierHandler {
    /**
     *
     * @return Plugin name
     */
    public String getPluginName();

    /**
     *
     * @return List of configuration name with its default value
     */
    public HashMap<String, String> getAvailableConfigurations();

    /**
     *
     * @param key Configuration name
     * @param value Configuration value
     */
    public void setAvailableConfigurations(String key, String value);

    /**
     *
     * @param dataset datatest in Instances representation which want to be classified
     * @param datatraining data training in Instances representation
     * @return datatest as Instances with class target value
     * @throws Exception
     */
    public Instances classify(Instances datatest, Instances datatraining) throws Exception;
}
