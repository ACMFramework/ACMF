<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<graphml>
			<key id="d0" for="node" attr.name="name" attr.type="string">
	    </key>
			<key id="d1" for="node" attr.name="visibility" attr.type="string">
	    </key>
			<key id="d2" for="node" attr.name="variableType" attr.type="string">
	    </key>
			<key id="d4" for="node" attr.name="parameters" attr.type="string">
	    </key>
			<key id="d5" for="node" attr.name="returnType" attr.type="string">
	    </key>
			<key id="d6" for="node" attr.name="type" attr.type="string">
	    </key>
		<key id="d7" for="edge" attr.name="relType" attr.type="string"/>
		<key id="d8" for="node" attr.name="uniqueId" attr.type="string">
    	</key>
		
			<graph id="DI" edgedefault="undirected">
				<xsl:for-each select="VisioDocument/Pages/Page/Shapes/Shape [(//Text)[position()!=1]]">
					<xsl:if test="Text !=''">
						<!-- Parameters-->
						<xsl:param name="container"><xsl:value-of select="Text"/></xsl:param>
						<xsl:param name="colonDelimiter" select="':'"/>
						<xsl:param name="bracketDelimiter" select="'('"/>
						<xsl:param name="closingBracketDelimiter" select="')'"/>
						<xsl:param name="bracketAndColonDelimiter" select="'):'"/>
						<xsl:param name="attributes">UMLAttribute</xsl:param>
						<xsl:param name="operations">UMLOperation</xsl:param>
						<xsl:param name="classType">class</xsl:param>
						
						<node>
							<xsl:attribute name="id">di<xsl:number/></xsl:attribute>
							<data key="d0">
								 <xsl:choose>
									 <!-- If string can be split into two parts, then it is either a method or a field-->
									 <xsl:when test="contains($container, ':')">
										<xsl:param name="firstPart">
											<xsl:value-of select="substring-before($container, $colonDelimiter)"/>
										</xsl:param>
										 <!-- If the first part of the string contains a bracket, it is a method -->
										 <!-- The name part will be the substring preceding the bracket-->
										 <xsl:choose>
											 <xsl:when test="contains($firstPart, '(')">
												 <xsl:value-of select="substring-before($firstPart, $bracketDelimiter)"/>
											 </xsl:when>
		 							    	<xsl:otherwise>
		 							             <xsl:value-of select="$firstPart"/>
		 							    	 </xsl:otherwise>
										 </xsl:choose>
										<xsl:param name="secondPart">
											<xsl:value-of select="substring-after($container, $colonDelimiter)"/>
										</xsl:param>
									</xsl:when>
								 <!-- If string cannot be split into two parts, then it is a class name-->
								 <!-- Return the entire string - text node-->
							    	<xsl:otherwise>
							             	<xsl:value-of select="Text"/>
							    	 </xsl:otherwise>
								</xsl:choose>
							</data>
							
							<data key="d1">
								<xsl:if test="contains($container, '(')">Public</xsl:if>
								<xsl:if test="not(contains($container, '('))">Private</xsl:if>
							</data>
							
							<!-- Display data key 2 only for attributes - attributes do not have brackets-->
							<!-- the colonDelimiter string parameter is used to get the relevant substring from the returned text 							node-->
							<xsl:if test="not(contains($container, '('))">
								<data key="d2">
									<xsl:value-of select="substring-after($container, $colonDelimiter)"/>
								</data>
							</xsl:if>
							
							<!-- Display data key 5 only for operations-->
							<xsl:if test="contains($container, '(')">
								<data key="d5">
									<xsl:value-of select="substring-after($container, $bracketAndColonDelimiter)"/>
								</data>
							</xsl:if>
							<!-- Display data key 4 only for operations-->
							<!-- Get contents of the brackets-->
							<xsl:if test="contains($container, '(')">
								<data key="d4">
								    <xsl:value-of select="
								                   substring-after(substring-before($container, $closingBracketDelimiter), 												   $bracketDelimiter)"/>									
								</data>
							<!-- Check if it is an attribute or an operation or a class -->
							</xsl:if>
							<data key="d6">
							 <xsl:choose>
								 <!-- If string contains brackets, it is a method-->
								 <xsl:when test="contains($container, '(')">
									 <xsl:value-of select="$operations"/>
								</xsl:when>
							 <!-- If string does not contain brackets, it is either an attribute or a class -->
						    	<xsl:otherwise>
   									 <xsl:choose>
   										<xsl:when test="contains($container, ':')">
   											<xsl:value-of select="$attributes"/>
   										 </xsl:when>
   	 							    	<xsl:otherwise>
											<xsl:value-of select="$classType"/>
   	 							    	 </xsl:otherwise>
   									 </xsl:choose>
						    	 </xsl:otherwise>
							</xsl:choose>
							</data>
							<data key="d8">
							</data>
						</node>
					</xsl:if>
				</xsl:for-each>
				<edge id="diE0" source="di3" target="di2">
					<data key="d1">Inheritance</data>
				</edge>
			</graph>
		</graphml>
	</xsl:template>
</xsl:stylesheet>
