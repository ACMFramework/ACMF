<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
<xsl:template match="/">
	<graphml>
	    <key id="d0" for="node"
	         attr.name="name" attr.type="string">
	    </key>
	    <key id="d1" for="node"
	         attr.name="visibility" attr.type="string">
	    </key>
	    <key id="d2" for="node"
	         attr.name="variableType" attr.type="string">
	    </key>
	    <key id="d3" for="node"
	         attr.name="content" attr.type="string">
	    </key>
	    <key id="d4" for="node"
	         attr.name="parameters" attr.type="string">
	    </key>
	    <key id="d5" for="node"
	         attr.name="returnType" attr.type="string">
	    </key>
	    <key id="d6" for="node"
	         attr.name="type" attr.type="string">
	    </key>
	    <key id="d7" for="edge"
	         attr.name="relType" attr.type="string"/>
	 	<key id="d8" for="node"
	 	      attr.name="uniqueId" attr.type="string">
	 	</key>
	 	<key id="d9" for="node"
	 	      attr.name="implementsOrExtends" attr.type="string">
	 	</key>
	 	    
	 	<xsl:param name="class">class</xsl:param>
		<xsl:param name="interface">interface</xsl:param>
		<xsl:param name="enum">enum</xsl:param>
		<xsl:param name="default">default</xsl:param>
		<xsl:param name="enumDelimiter" select="'m'"/>
		<xsl:param name="bracketDelimiter" select="'( '"/>
		<xsl:param name="closingBracketDelimiter" select="' )'"/>
		
		<graph id="SC" edgedefault="undirected">
			<node>
				<xsl:attribute name="id">sc<xsl:number/>
				</xsl:attribute>	  
				<data key="d0">
					<xsl:choose>
	    				<xsl:when test="count(/unit/enum) != 0 ">
	        			 	<xsl:value-of select="unit/*[3]/*[2]"/>
	    				</xsl:when>
	   					<xsl:otherwise>
	         				<xsl:value-of select="unit/class/name"/>
	    				</xsl:otherwise>
					</xsl:choose>	
				</data>
				
				<!-- In some cases visibility is not specified -->
				<data key="d1">
					<xsl:choose>
	    				<xsl:when test="count(/unit/enum) != 0 ">
	        			 	<xsl:value-of select="unit/*[3]/*[1]"/>
	    				</xsl:when>
	   					<xsl:otherwise>
	         				<xsl:value-of select="unit/class/specifier"></xsl:value-of>
	    				</xsl:otherwise>
					</xsl:choose>	
				</data>
				<data key="d6">
				<!-- The node names of the generated XML files can differ -->
				<!-- Specific values are given to the Type element based on the name of the node -->
					<xsl:choose>
	    				<xsl:when test="count(/unit/enum) != 0 ">
	        			 	<xsl:value-of select="$enum"/>
	    				</xsl:when>
	    				<xsl:when test="unit/class/@type">
	        			 	<xsl:value-of select="unit/class/@type"/>
	    				</xsl:when>
	    				<xsl:when test="/unit/class">
	        			 	<xsl:value-of select="$class"/>
	    				</xsl:when>
	   					<xsl:otherwise>
	         				<xsl:value-of select="$default"/>
	    				</xsl:otherwise>
					</xsl:choose>	
				<!--<xsl:value-of select="unit/*[3]/*[2]"/>-->
				</data>
				<data key="d8">
				</data>
				<data key="d9">
					<xsl:if test= "unit/class/super/implements/name">
						<xsl:value-of select="unit/class/super/implements/name"/>
					</xsl:if>
					<xsl:if test= "unit/class/super/extends/name">
						<xsl:value-of select="unit/class/super/extends/name"/>
					</xsl:if>
				</data>
			</node>
			
				<xsl:for-each select="unit/class/block/decl_stmt">
					<node>
						<xsl:attribute name="id">sc<xsl:number/>
						</xsl:attribute>	
				        <data key="d0">
							<xsl:value-of select="decl/name"/>
						</data>
						
						<!-- In some cases visibility is not specified -->
				    	<data key="d1">
							<xsl:value-of select="decl/type/specifier"/>
						</data>
				    	<data key="d2">
							<xsl:value-of select="decl/type/name"/>
						</data>
				    	<data key="d6">Field</data>
						<data key="d8"></data>
					</node>
				</xsl:for-each>
				
				<xsl:for-each select="unit/class/block/constructor">
					<node>
						<xsl:attribute name="id">sc<xsl:number/>
						</xsl:attribute>	
				        <data key="d0">
							<xsl:value-of select="name"/>
						</data>
				    	<data key="d1">
							<xsl:value-of select="specifier"/>
						</data>
				    	<data key="d3">
							<xsl:value-of select="block"/>
						</data>
						<!-- If there are no parameters, display an empty string instead of a bracket -->
						<!-- Else if there are parameters, get rid of the brackets surrounding them -->
				    	<data key="d4">
				    		<xsl:choose>
								<xsl:when test="string-length(parameter_list)=2">
									<xsl:value-of select="''"/>
							  	</xsl:when>
		 						<xsl:otherwise>
		 							<!--<xsl:value-of select="substring-before(substring-after(parameter_list, $bracketDelimiter), $closingBracketDelimiter)"/>-->
		 							<xsl:value-of select="parameter_list"></xsl:value-of>
		 						</xsl:otherwise>    	
		 					</xsl:choose>	
						</data>
				    	<data key="d5">
						</data>
				    	<data key="d6">Method</data>
						<data key="d8"></data>
					</node>
				</xsl:for-each>
				
				<xsl:for-each select="unit/class/block/function">	
					<node>
						<xsl:attribute name="id">sc<xsl:number/>
						</xsl:attribute>	
				        <data key="d0">
							<xsl:value-of select="name"/>
						</data>
				    	<data key="d1">
							<xsl:value-of select="type/specifier"/>
						</data>
				    	<data key="d3">
							<xsl:value-of select="block"/>
						</data>
						<!-- When methods are overriden, the return type value can be accessed in a different way -->
				    	<data key="d5">
							<xsl:choose>
								<xsl:when test="type/name='Override'">
									<xsl:value-of select="(type/*[3])"/>
							  	</xsl:when>
							  	<xsl:when test="type/name='Test'">
									<xsl:value-of select="(type/*[3])"/>
							  	</xsl:when>
		 						<xsl:otherwise>
		 							<xsl:value-of select="type/name"/>
		 						</xsl:otherwise>    	
		 					</xsl:choose>	
						</data>
						<!-- If there are no parameters, display an empty string instead of a bracket -->
						<!-- Else if there are parameters, get rid of the brackets surrounding them -->
				    	<data key="d4">
				    		<xsl:choose>
								<xsl:when test="string-length(parameter_list)=2">
									<xsl:value-of select="''"/>
							  	</xsl:when>
		 						<xsl:otherwise>
		 							<!--<xsl:value-of select="substring-before(substring-after(parameter_list, $bracketDelimiter), $closingBracketDelimiter)"/> -->
		 							<xsl:value-of select="parameter_list"></xsl:value-of>
		 						</xsl:otherwise>    	
		 					</xsl:choose>	
						</data>
				    	<data key="d6">Method</data>
						<data key="d8"></data>
					</node>
				</xsl:for-each>
				
				<xsl:for-each select="unit/class/block/function_decl">
					<node>
						<xsl:attribute name="id">sc<xsl:number/>
						</xsl:attribute>	
				        <data key="d0">
							<xsl:value-of select="name"/>
						</data>
				    	<data key="d1">
							<xsl:value-of select="type/specifier"/>
						</data>
				    	<data key="d3">
							<xsl:value-of select="block"/>
						</data>
				    	<!-- When methods are overriden, the return type value can be accessed in a different way -->
				    	<data key="d5">
							<xsl:choose>
								<xsl:when test="type/name='Override'">
									<xsl:value-of select="(type/*[3])"/>
							  	</xsl:when>
		 						<xsl:otherwise>
		 							<xsl:value-of select="type/name"/>
		 						</xsl:otherwise>    	
		 					</xsl:choose>	
						</data>
				    	<!-- If there are no parameters, display an empty string instead of a bracket -->
						<!-- Else if there are parameters, get rid of the brackets surrounding them -->
				    	<data key="d4">
				    		<xsl:choose>
								<xsl:when test="string-length(parameter_list)=2">
									<xsl:value-of select="''"/>
							  	</xsl:when>
		 						<xsl:otherwise>
		 							<!--<xsl:value-of select="substring-before(substring-after(parameter_list, $bracketDelimiter), $closingBracketDelimiter)"/> -->
		 						<xsl:value-of select="parameter_list"></xsl:value-of>
		 						</xsl:otherwise>    	
		 					</xsl:choose>	
						</data>
				    	<data key="d6">Method</data>
						<data key="d8"></data>
					</node>
				</xsl:for-each>
				
				<xsl:for-each select="unit/class/block/decl_stmt">
					<edge id="" source ="" target="">
      					<data key="d7">Parent_Child</data>
					</edge>
				</xsl:for-each>
				<xsl:for-each select="unit/class/block/constructor">
					<edge id="" source="" target="">
      					<data key="d7">Parent_Child</data>
					</edge>
				</xsl:for-each>
				<xsl:for-each select="unit/class/block/function">
					<edge id="" source="" target="">
      					<data key="d7">Parent_Child</data>
					</edge>
				</xsl:for-each>
				<xsl:for-each select="unit/class/block/function_decl">
					<edge id="" source="" target="">
      					<data key="d7">Parent_Child</data>
					</edge>
				</xsl:for-each>
		</graph>
	</graphml>
</xsl:template>
</xsl:stylesheet>