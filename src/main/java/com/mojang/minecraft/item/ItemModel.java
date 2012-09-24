package com.mojang.minecraft.item;

import com.mojang.minecraft.model.ModelPart;
import com.mojang.minecraft.model.TexturedQuad;
import com.mojang.minecraft.model.Vertex;

public class ItemModel
{
	public ItemModel(int tex)
	{
		float var3 = -2.0F;
		float var4 = -2.0F;
		float var15 = -2.0F;

		model.vertices = new Vertex[8];
		model.quads = new TexturedQuad[6];

		Vertex vertex1 = new Vertex(var15, var4, var3, 0.0F, 0.0F);
		Vertex vertex2 = new Vertex(2.0F, var4, var3, 0.0F, 8.0F);
		Vertex vertex3 = new Vertex(2.0F, 2.0F, var3, 8.0F, 8.0F);
		Vertex vertex4 = new Vertex(var15, 2.0F, var3, 8.0F, 0.0F);
		Vertex vertex5 = new Vertex(var15, var4, 2.0F, 0.0F, 0.0F);
		Vertex vertex6 = new Vertex(2.0F, var4, 2.0F, 0.0F, 8.0F);
		Vertex vertex7 = new Vertex(2.0F, 2.0F, 2.0F, 8.0F, 8.0F);
		Vertex vertex8 = new Vertex(var15, 2.0F, 2.0F, 8.0F, 0.0F);

		model.vertices[0] = vertex1;
		model.vertices[1] = vertex2;
		model.vertices[2] = vertex3;
		model.vertices[3] = vertex4;
		model.vertices[4] = vertex5;
		model.vertices[5] = vertex6;
		model.vertices[6] = vertex7;
		model.vertices[7] = vertex8;

		float u1 = ((float)(tex % 16) + (1.0F - 0.25F)) / 16.0F;
		float v1 = ((float)(tex / 16) + (1.0F - 0.25F)) / 16.0F;
		float u2 = ((float)(tex % 16) + 0.25F) / 16.0F;
		float v2 = ((float)(tex / 16) + 0.25F) / 16.0F;

		Vertex[] vertexes1 = new Vertex[] {vertex6, vertex2, vertex3, vertex7};
		Vertex[] vertexes2 = new Vertex[] {vertex1, vertex5, vertex8, vertex4};
		Vertex[] vertexes3 = new Vertex[] {vertex6, vertex5, vertex1, vertex2};
		Vertex[] vertexes4 = new Vertex[] {vertex3, vertex4, vertex8, vertex7};
		Vertex[] vertexes5 = new Vertex[] {vertex2, vertex1, vertex4, vertex3};
		Vertex[] vertexes6 = new Vertex[] {vertex5, vertex6, vertex7, vertex8};

		model.quads[0] = new TexturedQuad(vertexes1, u1, v1, u2, v2);
		model.quads[1] = new TexturedQuad(vertexes2, u1, v1, u2, v2);
		model.quads[2] = new TexturedQuad(vertexes3, u1, v1, u2, v2);
		model.quads[3] = new TexturedQuad(vertexes4, u1, v1, u2, v2);
		model.quads[4] = new TexturedQuad(vertexes5, u1, v1, u2, v2);
		model.quads[5] = new TexturedQuad(vertexes6, u1, v1, u2, v2);
	}

	private ModelPart model = new ModelPart(0, 0);

	public void generateList()
	{
		model.render(0.0625F);
	}
}
