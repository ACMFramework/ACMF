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
	         attr.name="title" attr.type="string">
	    </key>
	    <key id="d2" for="node"
	         attr.name="content" attr.type="string">
	    </key>
	    <key id="d3" for="node"
	         attr.name="priority" attr.type="string">
	    </key>
	    <key id="d4" for="node"
	         attr.name="type" attr.type="string">
	    </key>
	    <key id="d5" for="edge"
	         attr.name="relType" attr.type="string"/>
	 	<key id="d8" for="node"
	 	     attr.name="uniqueId" attr.type="string">
	 	</key>
		
		<graph id="RQ" edgedefault="undirected">
		
		
		<xsl:for-each select="requirement/h">
		<xsl:if test="@outline-level ='4'">
			<xsl:param name="container">
				<xsl:value-of select="."/>
			</xsl:param>
			<xsl:param name="colonDelimiter" select="':'"/>
			<xsl:param name="priorityParam">
				<xsl:value-of select="//requirement/h/following-sibling::p[7]"/>
			</xsl:param>
			<xsl:param name="typeParam">
				<xsl:value-of select="//requirement/h/following-sibling::p[6]"/>
			</xsl:param>
			<node>
				<xsl:attribute name="id">rq<xsl:number/>
				</xsl:attribute>
				<data key="d0">
					<xsl:value-of select="substring($container, 1, 2)"/>	
				</data>
			
				<data key="d1">
					<xsl:value-of select="substring($container, 3, string-length($container))"/>	
				</data>
			
				<data key="d2">
					<xsl:value-of select="//requirement/h/following-sibling::p[5]"/>
				</data>
		
				<data key="d3">
					<xsl:value-of select="substring-after($priorityParam, $colonDelimiter)"/>
				</data>
			
				<data key="d4">
					<xsl:value-of select="substring-after($typeParam, $colonDelimiter)"/>
				</data>
				
				<data key="d8">
				</data>
			</node>
	</xsl:if>
	</xsl:for-each>
    <edge id="re0" source="rq3" target="rq2">
      <data key="d1">DependsOn</data>
    </edge>
	</graph>
	</graphml>
</xsl:template>
</xsl:stylesheet>