using UnityEngine;

public class SpikeVisual : MonoBehaviour
{
    private static Sprite triangleSprite;
    private static Sprite squareSprite;

    void Awake()
    {
        if (triangleSprite == null) triangleSprite = CreateTriangleSprite();
        if (squareSprite == null) squareSprite = CreateSquareSprite();

        SpriteRenderer sr = GetComponent<SpriteRenderer>();
        if (sr != null)
        {
            if (transform.localScale.x <= 1.5f) {
                sr.sprite = triangleSprite;
            } else {
                sr.sprite = squareSprite; // ¡Ahora sí se verán!
            }
            sr.drawMode = SpriteDrawMode.Simple;
        }
    }

    private Sprite CreateSquareSprite()
    {
        int size = 128; // Tamaño mayor para visibilidad
        Texture2D tex = new Texture2D(size, size);
        for(int y=0; y<size; y++) for(int x=0; x<size; x++) tex.SetPixel(x,y, Color.white);
        tex.Apply();
        return Sprite.Create(tex, new Rect(0,0,size,size), new Vector2(0.5f, 0.5f));
    }

    private Sprite CreateTriangleSprite()
    {
        int size = 128;
        Texture2D texture = new Texture2D(size, size);
        Color transparent = new Color(0, 0, 0, 0);
        Color white = Color.white;

        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                // Ecuación para dibujar un triángulo (isósceles apuntando arriba)
                // Usamos la lógica: y <= (size - 1) * (1.0 - 2.0 * Mathf.Abs(x / (float)(size - 1) - 0.5f))
                float normalizedX = x / (float)(size - 1);
                float normalizedY = y / (float)(size - 1);
                
                if (normalizedY <= 1.0f - 2.0f * Mathf.Abs(normalizedX - 0.5f))
                {
                    texture.SetPixel(x, y, white);
                }
                else
                {
                    texture.SetPixel(x, y, transparent);
                }
            }
        }
        texture.Apply();
        return Sprite.Create(texture, new Rect(0, 0, size, size), new Vector2(0.5f, 0f)); // Pivote abajo
    }
}
