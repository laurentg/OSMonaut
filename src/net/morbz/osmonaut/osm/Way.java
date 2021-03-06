package net.morbz.osmonaut.osm;

/*
* The MIT License (MIT)
* 
* Copyright (c) 2015 Merten Peetz
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

import java.util.List;

import net.morbz.osmonaut.geometry.Bounds;
import net.morbz.osmonaut.geometry.Polygon;
import net.morbz.osmonaut.util.StringUtil;

/**
 * A class that represents an OSM way element.
 * @author MorbZ
 */
public class Way extends Entity {
	private List<Node> nodes;
	
	/**
	 * @param id The OSM-ID of this way
	 * @param tags The tags of this way
	 * @param nodes The nodes of this way
	 */
	public Way(long id, Tags tags, List<Node> nodes) {
		super(id, tags);
		this.nodes = nodes;
	}
	
	/**
	 * @return The nodes of this way
	 */
	public List<Node> getNodes() {
		return nodes;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityType getEntityType() {
		return EntityType.WAY;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public LatLon getCenter() {
		// Use a polygon to get the centroid
		Polygon poly = new Polygon(this);
		return poly.getCenter();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Bounds getBounds() {
		Bounds bounds = new Bounds();
		for(Node node : nodes) {
			bounds.extend(node.getLatlon());
		}
		return bounds;
	}
	
	/**
	 * Whether the way is closed. A closed way means its first and last nodes are identical.
	 * @return True if the way is closed
	 */
	public boolean isClosed() {
		if(nodes.size() <= 2) {
			return false;
		}
		return nodes.get(0).equals(nodes.get(nodes.size()-1));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String str = "";
		str += "{" + "\t" + "WAY" + "\n";
		str += "\t" + "id: " + id + "\n";
		str += "\t" + "tags: " + StringUtil.indent(tags.toString());
		str += "\t" + "nodes: [" + "\n";
		for(Node node : nodes) {
			str += StringUtil.indent(StringUtil.indent(node.toString()));
		}
		str += "\t" + "]" + "\n";
		str += "}";
		return str;		
	}
}
