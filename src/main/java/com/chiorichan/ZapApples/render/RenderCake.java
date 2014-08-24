package com.chiorichan.ZapApples.render;

import com.chiorichan.ZapApples.ZapApples;
import com.chiorichan.ZapApples.blocks.BlockCake;
import com.chiorichan.ZapApples.blocks.BlockCake.CakeIngredientMap;
import com.chiorichan.ZapApples.tiles.TileEntityCake;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Icon;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;

public class RenderCake
  implements ISimpleBlockRenderingHandler
{
  public RenderCake()
  {
    ZapApples.idRenderCake = RenderingRegistry.getNextAvailableRenderId();
    RenderingRegistry.registerBlockHandler(this);
  }

  public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
  {
    if (block.blockID == ZapApples.cake.blockID)
    {
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, -1.0F, 0.0F);
      block.setBlockBoundsForItemRender();

      ItemStack stack = new ItemStack(block, 1, metadata);

      String base = "2";
      String frosting = "6";

      BlockCake.CakeIngredientMap ingred = ZapApples.cake.getBaseIngredient(base) == null ? ZapApples.cake.getBaseIngredient("plain") : ZapApples.cake.getBaseIngredient(base);
      Icon icon = ingred.getIcon(3, true);
      renderer.renderFaceZNeg(block, -0.5D, -0.5D, -0.5D, icon);
      renderer.renderFaceZPos(block, -0.5D, -0.5D, -0.5D, icon);
      renderer.renderFaceXNeg(block, -0.5D, -0.5D, -0.5D, ingred.getIcon(4, true));
      renderer.renderFaceXPos(block, -0.5D, -0.5D, -0.5D, icon);
      renderer.renderFaceYNeg(block, -0.5D, -0.5D, -0.5D, ingred.getIcon(0, true));

      ingred = ZapApples.cake.getFrostIngredient(frosting) == null ? ZapApples.cake.getFrostIngredient("plain") : ZapApples.cake.getFrostIngredient(frosting);
      icon = ingred.getIcon(3, true);
      renderer.renderFaceZNeg(block, -0.5D, -0.5D, -0.5D, icon);
      renderer.renderFaceZPos(block, -0.5D, -0.5D, -0.5D, icon);
      renderer.renderFaceXNeg(block, -0.5D, -0.5D, -0.5D, ingred.getIcon(4, true));
      renderer.renderFaceXPos(block, -0.5D, -0.5D, -0.5D, icon);
      renderer.renderFaceYPos(block, -0.5D, -0.5D, -0.5D, ingred.getIcon(1, true));

      tessellator.draw();
    }
  }

  public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
  {
    if (modelID == getRenderId())
    {
      if (block.blockID == ZapApples.cake.blockID)
      {
        TileEntityCake tile = (TileEntityCake)world.getBlockTileEntity(x, y, z);

        if (tile != null)
        {
          renderCake(world, x, y, z, block, renderer, world.getBlockMetadata(x, y, z), tile);
        }
      }

      return true;
    }
    return false;
  }

  private void renderCake(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer, int meta, TileEntityCake tile)
  {
    BlockCake cake = (BlockCake)block;
    cake.setBlockBoundsBasedOnState(world, x, y, z);

    if (tile != null)
    {
      BlockCake.CakeIngredientMap ingred = ZapApples.cake.getBaseIngredient(tile.base) == null ? ZapApples.cake.getBaseIngredient("plain") : ZapApples.cake.getBaseIngredient(tile.base);
      Icon icon = ingred.getIcon(3, tile.stage < 1);
      renderer.renderFaceZNeg(cake, x, y, z, icon);
      renderer.renderFaceZPos(cake, x, y, z, ingred.getIcon(4, tile.stage < 1));
      renderer.renderFaceXNeg(cake, x, y, z, icon);
      renderer.renderFaceXPos(cake, x, y, z, icon);
      renderer.renderFaceYNeg(cake, x, y, z, ingred.getIcon(0, tile.stage < 1));

      ingred = ZapApples.cake.getFrostIngredient(tile.frost) == null ? ZapApples.cake.getFrostIngredient("plain") : ZapApples.cake.getFrostIngredient(tile.frost);
      icon = ingred.getIcon(3, tile.stage < 1);
      renderer.renderFaceZNeg(cake, x, y, z, icon);
      renderer.renderFaceZPos(cake, x, y, z, ingred.getIcon(4, tile.stage < 1));
      renderer.renderFaceXNeg(cake, x, y, z, icon);
      renderer.renderFaceXPos(cake, x, y, z, icon);
      renderer.renderFaceYPos(cake, x, y, z, ingred.getIcon(1, tile.stage < 1));
    }
  }

  public boolean shouldRender3DInInventory()
  {
    return true;
  }

  public int getRenderId()
  {
    return ZapApples.idRenderCake;
  }
}