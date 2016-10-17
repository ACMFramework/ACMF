<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited by XMLSpy® -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
<xsl:template match="/">
	<graphml>
	   <key id="d0" for="node"
	         attr.name="name" attr.type="string">
	    </key>
	    <key id="d6" for="node"
	         attr.name="type" attr.type="string">
	    </key>
	    <key id="d7" for="edge"
	         attr.name="annotation" attr.type="string">
	    </key>
		<key id="d8" for="node" 
			attr.name="uniqueId" attr.type="string">
		</key>

			<graph id="AR" edgedefault="undirected">
				<xsl:for-each select="VisioDocument/Pages/Page/Shapes/Shape [(//Text)[position()!=1]]">
					<xsl:if test="Text !=''">
						<!-- Parameters-->
						<xsl:param name="type">UseCase</xsl:param>
						
						<node>
							<xsl:attribute name="id">uc<xsl:number/></xsl:attribute>
							<data key="d0">
							    <xsl:value-of select="Text"/>
							</data>
						
							<data key="d6">
								<xsl:value-of select="$type"/>
							</data>
							<data key="d8">
							</data>
						</node>
					</xsl:if>
				</xsl:for-each>
				<edge id="ucE0" source="" target="">
					<data key="d7"></data>
				</edge>
			</graph>
		</graphml>
</xsl:template>
</xsl:stylesheet>