package framework.changeDetection;

import java.util.List;
import java.util.Map;

import com.google.common.collect.MapDifference.ValueDifference;

/** Enum representing signature change value possibilities
 * 
 *
 */
public enum SignatureChange 
{
	/**
	 * 
	 */
	TRUE,
	FALSE,
	BOTH;

	/**
	 * 
	 * @return
	 */
	public static SignatureChange isSignatureChange(List<ChangeData> changes)
	{
		SignatureChange result = SignatureChange.FALSE;
		for(int i = 0; i < changes.size(); i++) 
		{
			if(changes.get(i).getTypeOfChange() == ArtefactElementChangeType.EDIT && changes.get(i).getTypeOfArtefact() == ArtefactType.JAVA_SOURCE_CODE
					|| changes.get(i).getTypeOfArtefact() == ArtefactType.UNIT_TEST) 
			{
				List<Map< String, ValueDifference<String>>> edits = changes.get(i).getEdits();

				if(edits.iterator().next().keySet().contains("d0")) 
				{
					result = SignatureChange.TRUE;
				}
			}
		}

		return result;
	}
}
