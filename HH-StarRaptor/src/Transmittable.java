
public interface Transmittable {

	/**
	 * create a string describing just the information needed to <i>update</i> this object
	 * @return description
	 */
	public String shortDescription();
	
	/**
	 * create a string describing the information needed to <i>create</i> this object
	 * @return description
	 */
	public String longDescription();
	
	/**
	 * set the fields for this class, based on a description string of the form 
	 * made by longDescription().
	 * @param longDesc
	 */
	public void buildFromDescription(String longDesc);
	
	/**
	 * update the fields for this class, based on a description string of the form 
	 * made by shortDescription().
	 * @param shortDesc
	 */
	public void updateFromDescription(String shortDesc);
	
}
