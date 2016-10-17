<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited by XMLSpy® -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
<xsl:template match="/">
	<graphml>
	   <key id="d0" for="node"
	         attr.name="name" attr.type="string">
	    </key>
	    <key id="d1" for="node"
	         attr.name="responsibilities" attr.type="string">
	    </key>
	    <key id="d2" for="node"
	         attr.name="visibility" attr.type="string">
	    </key>		
	    <key id="d6" for="node" 
	    	attr.name="type" attr.type="string">
		</key>
		
			<graph id="AR" edgedefault="undirected">
				<xsl:for-each select="VisioDocument/Pages/Page/Shapes/Shape [(//Text)[position()!=1]]">
					<xsl:if test="Text !=''">
						<!-- Parameters-->
						<xsl:param name="container"><xsl:value-of select="Text"/></xsl:param>
						<xsl:param name="colonDelimiter" select="':'"/>
						<xsl:param name="bracketDelimiter" select="'('"/>
						<xsl:param name="closingBracketDelimiter" select="')'"/>
						<xsl:param name="bracketAndColonDelimiter" select="'):'"/>
						<xsl:param name="moduleType">module</xsl:param>
						
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
							</data>
						
							<data key="d2">
							</data>
						
							<data key="d6">
								<xsl:value-of select="$moduleType"/>
							</data>
							<data key="d8">
							</data>
						</node>
					</xsl:if>
				</xsl:for-each>
				<edge id="diE0" source="" target="">
					<data key="d1"></data>
				</edge>
			</graph>
		</graphml>
</xsl:template>
</xsl:stylesheet>